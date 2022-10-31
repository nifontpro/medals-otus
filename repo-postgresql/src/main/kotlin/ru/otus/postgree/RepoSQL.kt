package ru.otus.postgree

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.helpers.errorRepoConcurrency
import ru.otus.model.*
import ru.otus.repo.*
import java.sql.SQLException

private const val notFoundCode = "not-found"

class RepoCommentSQL(
	url: String = "jdbc:postgresql://localhost:5432/marketplacedevdb",
	user: String = "postgres",
	password: String = "marketplace-pass",
	schema: String = "marketplace",
	initObjects: Collection<MkplComment> = emptyList(),
	val randomUuid: () -> String = { uuid4().toString() },
) : ICommentRepository {

	private val db by lazy { SqlConnector(url, user, password, schema).connect(CommentsTable, EntityTable) }

	init {
		initObjects.forEach {
			save(it)
		}
	}

	private fun save(item: MkplComment): DbCommentResponse {
		return safeTransaction({

			val realEntityId = EntityTable.insertIgnore {

				if (item.entityId != MkplEntityId.NONE) {
					it[id] = item.entityId.asString()
				}

			} get EntityTable.id

			val res = CommentsTable.insert {
				if (item.id != MkplCommentId.NONE) {
					it[id] = item.id.asString()
				}
				it[comment] = item.comment
				it[rating] = item.rating
				it[entityId] = realEntityId
				it[entityType] = item.entityType
				it[lock] = item.lock.asString()
			}

			DbCommentResponse(CommentsTable.from(res), true)
		}, {
			DbCommentResponse(
				data = null,
				isSuccess = false,
				errors = listOf(MkplError(message = message ?: localizedMessage))
			)
		})
	}

	override suspend fun createComment(request: DbCommentRequest): DbCommentResponse {
		val ad = request.comment.copy(lock = MkplLock(randomUuid()))
		return save(ad)
	}

	override suspend fun getByIdComment(request: DbCommentIdRequest): DbCommentResponse {
		return safeTransaction({
			val result = (CommentsTable innerJoin EntityTable).select { CommentsTable.id.eq(request.id.asString()) }.single()

			DbCommentResponse(CommentsTable.from(result), true)
		}, {
			val err = when (this) {
				is NoSuchElementException -> MkplError(field = "id", message = "Not Found", code = notFoundCode)
				is IllegalArgumentException -> MkplError(message = "More than one element with the same id")
				else -> MkplError(message = localizedMessage)
			}
			DbCommentResponse(data = null, isSuccess = false, errors = listOf(err))
		})
	}

	override suspend fun updateComment(request: DbCommentRequest): DbCommentResponse {
		val key = request.comment.id.takeIf { it != MkplCommentId.NONE }?.asString() ?: return resultErrorEmptyId
		val oldLock = request.comment.lock.takeIf { it != MkplLock.NONE }?.asString()
		val newComment = request.comment.copy(lock = MkplLock(randomUuid()))

		return safeTransaction({
			val local = CommentsTable.select { CommentsTable.id.eq(key) }.singleOrNull()?.let {
				CommentsTable.from(it)
			} ?: return@safeTransaction resultErrorNotFound

			return@safeTransaction when (oldLock) {
				null, local.lock.asString() -> updateDb(newComment)
				else -> resultErrorConcurrent(local.lock.asString(), local)
			}
		}, {
			DbCommentResponse(
				data = request.comment,
				isSuccess = false,
				errors = listOf(MkplError(field = "id", message = "Not Found", code = notFoundCode))
			)
		})
	}


	private fun updateDb(newComment: MkplComment): DbCommentResponse {
		EntityTable.insertIgnore {
			if (newComment.entityId != MkplEntityId.NONE) {
				it[id] = newComment.entityId.asString()
			}
		}

		CommentsTable.update({ CommentsTable.id.eq(newComment.id.asString()) }) {
			it[comment] = newComment.comment
			it[rating] = newComment.rating
			it[entityId] = newComment.entityId.asString()
			it[entityType] = newComment.entityType
			it[lock] = newComment.lock.asString()
		}
		val result = CommentsTable.select { CommentsTable.id.eq(newComment.id.asString()) }.single()

		return DbCommentResponse(data = CommentsTable.from(result), isSuccess = true)
	}

	override suspend fun deleteComment(request: DbCommentIdRequest): DbCommentResponse {
		val key = request.id.takeIf { it != MkplCommentId.NONE }?.asString() ?: return resultErrorEmptyId

		return safeTransaction({
			val local = CommentsTable.select { CommentsTable.id.eq(key) }.single().let { CommentsTable.from(it) }
			if (local.lock == request.lock) {
				CommentsTable.deleteWhere { CommentsTable.id eq request.id.asString() }
				DbCommentResponse(data = local, isSuccess = true)
			} else {
				resultErrorConcurrent(request.lock.asString(), local)
			}
		}, {
			DbCommentResponse(
				data = null,
				isSuccess = false,
				errors = listOf(MkplError(field = "id", message = "Not Found"))
			)
		})
	}

	override suspend fun getAllComments(): DbCommentsResponse {
		return safeTransaction({
			val results = (CommentsTable innerJoin EntityTable).selectAll()
			DbCommentsResponse(data = results.map { CommentsTable.from(it) }, isSuccess = true)
		}, {
			DbCommentsResponse(data = emptyList(), isSuccess = false, listOf(MkplError(message = localizedMessage)))
		})
	}

	/**
	 * Transaction wrapper to safely handle caught exception and throw all sql-like exceptions. Also remove of duplication code
	 */
	private fun <T> safeTransaction(statement: Transaction.() -> T, handleException: Throwable.() -> T): T {
		return try {
			transaction(db, statement)
		} catch (e: SQLException) {
			throw e
		} catch (e: Throwable) {
			return handleException(e)
		}
	}

	companion object {
		val resultErrorEmptyId = DbCommentResponse(
			data = null,
			isSuccess = false,
			errors = listOf(
				MkplError(
					field = "id",
					message = "Id must not be null or blank"
				)
			)
		)

		fun resultErrorConcurrent(lock: String, comment: MkplComment?) = DbCommentResponse(
			data = comment,
			isSuccess = false,
			errors = listOf(
				errorRepoConcurrency(MkplLock(lock), comment?.lock?.let { MkplLock(it.asString()) }
				)
			))

		val resultErrorNotFound = DbCommentResponse(
			isSuccess = false,
			data = null,
			errors = listOf(
				MkplError(
					field = "id",
					message = "Not Found",
					code = notFoundCode
				)
			)
		)
	}
}