package ru.otus.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.helpers.errorRepoConcurrency
import ru.otus.inmemory.model.CommentEntity
import ru.otus.model.MkplComment
import ru.otus.model.MkplCommentId
import ru.otus.model.MkplError
import ru.otus.model.MkplLock
import ru.otus.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class CommentRepoInMemory(
	initObjects: List<MkplComment> = emptyList(),
	ttl: Duration = 2.minutes,
	val randomUuid: () -> String = { uuid4().toString() },
) : ICommentRepository {

	/**
	 * Инициализация кеша с установкой "времени жизни" данных после записи
	 */
	private val cache = Cache.Builder()
		.expireAfterWrite(ttl)
		.build<String, CommentEntity>()

	private val mutex: Mutex = Mutex()

	init {
		initObjects.forEach {
			save(it)
		}
	}

	private fun save(comment: MkplComment) {
		val entity = CommentEntity(comment)
		if (entity.id == null) {
			return
		}
		cache.put(entity.id, entity)
	}


	override suspend fun createComment(request: DbCommentRequest): DbCommentResponse {
		val key = randomUuid()
		val comment = request.comment.copy(id = MkplCommentId(key), lock = MkplLock(randomUuid()))
		val entity = CommentEntity(comment)
		cache.put(key, entity)
		return DbCommentResponse(
			data = comment,
			isSuccess = true
		)
	}

	override suspend fun getAllComments(): DbCommentsResponse {
		val list = cache.asMap().map { it.value.toInternal() }
		return DbCommentsResponse(
			data = list,
			isSuccess = true
		)
	}

	override suspend fun getByIdComments(request: DbCommentIdRequest): DbCommentResponse {
		val key = request.id.takeIf { it != MkplCommentId.NONE }?.asString() ?: return resultErrorEmptyId
		return cache.get(key)
			?.let {
				DbCommentResponse(
					data = it.toInternal(),
					isSuccess = true
				)
			} ?: resultErrorNotFound
	}

	override suspend fun updateComment(request: DbCommentRequest): DbCommentResponse {
		val key = request.comment.id.takeIf { it != MkplCommentId.NONE }?.asString() ?: return resultErrorEmptyId
		val oldLock = request.comment.lock.takeIf { it != MkplLock.NONE }?.asString() ?: return resultErrorEmptyLock
		val newComment = request.comment.copy(lock = MkplLock(randomUuid()))
		val entity = CommentEntity(newComment)
		return mutex.withLock {
			val oldComment = cache.get(key)

			when {
				oldComment == null -> resultErrorNotFound

				oldComment.lock != oldLock -> DbCommentResponse(
					data = oldComment.toInternal(),
					isSuccess = false,
					errors = listOf(errorRepoConcurrency(MkplLock(oldLock), oldComment.lock?.let { MkplLock(it) }))
				)

				else -> {
					cache.put(key, entity)
					DbCommentResponse(
						data = newComment,
						isSuccess = true
					)
				}
			}
		}
	}

	override suspend fun deleteComment(request: DbCommentIdRequest): DbCommentResponse {
		val key = request.id.takeIf { it != MkplCommentId.NONE }?.asString() ?: return resultErrorEmptyId
		val oldLock = request.lock.takeIf { it != MkplLock.NONE }?.asString() ?: return resultErrorEmptyLock

		return mutex.withLock {
			val oldComment = cache.get(key)
			when {
				oldComment == null -> resultErrorNotFound

				oldComment.lock != oldLock -> DbCommentResponse(
					data = oldComment.toInternal(),
					isSuccess = false,
					errors = listOf(errorRepoConcurrency(MkplLock(oldLock), oldComment.lock?.let { MkplLock(it) }))
				)

				else -> {
					cache.invalidate(key)
					DbCommentResponse(
						data = oldComment.toInternal(),
						isSuccess = true
					)
				}
			}
		}
	}


	companion object {
		val resultErrorEmptyId = DbCommentResponse(
			data = null,
			isSuccess = false,
			errors = listOf(
				MkplError(
					code = "id-empty",
					group = "validation",
					field = "id",
					message = "Id must not be null or blank"
				)
			)
		)
		val resultErrorEmptyLock = DbCommentResponse(
			data = null,
			isSuccess = false,
			errors = listOf(
				MkplError(
					code = "lock-empty",
					group = "validation",
					field = "lock",
					message = "Lock must not be null or blank"
				)
			)
		)
		val resultErrorNotFound = DbCommentResponse(
			isSuccess = false,
			data = null,
			errors = listOf(
				MkplError(
					code = "not-found",
					field = "id",
					message = "Not Found"
				)
			)
		)
	}
}