package stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.MkplContext
import ru.otus.bussines.CommentProcessor
import ru.otus.model.*
import ru.otus.stubs.MkplStubs
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CommentUpdateStubTest {

	private val processor = CommentProcessor()
	private val id = MkplCommentId("CA1")
	private val entityId = MkplEntityId("EA111")
	private val comment = "Отличный товар для дома"
	private val rating = 5
	private val entityType = MkplEntityType.AD

	@Test
	fun create() = runTest {

		val ctx = MkplContext(
			command = MkplCommand.UPDATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.SUCCESS,
			commentRequest = MkplComment(
				id = id,
				entityId = entityId,
				comment = comment,
				rating = rating,
				entityType = entityType
			)
		)
		processor.exec(ctx)
		assertEquals(id, ctx.commentResponse.id)
		assertEquals(entityId, ctx.commentResponse.entityId)
		assertEquals(entityType, ctx.commentResponse.entityType)
		assertEquals(comment, ctx.commentResponse.comment)
		assertEquals(rating, ctx.commentResponse.rating)
	}

	@Test
	fun badId() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.UPDATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.BAD_ID,
			commentRequest = MkplComment()
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("id", ctx.errors.firstOrNull()?.field)
		assertEquals("validation", ctx.errors.firstOrNull()?.group)
	}

	@Test
	fun badComment() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.UPDATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.BAD_COMMENT,
			commentRequest = MkplComment(
				id = id,
				entityId = entityId,
				comment = comment,
				rating = rating,
				entityType = entityType
			)
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("comment", ctx.errors.firstOrNull()?.field)
		assertEquals("validation", ctx.errors.firstOrNull()?.group)
	}

	@Test
	fun badRating() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.UPDATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.BAD_RATING,
			commentRequest = MkplComment(
				id = id,
				entityId = entityId,
				comment = comment,
				rating = rating,
				entityType = entityType
			)
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("rating", ctx.errors.firstOrNull()?.field)
		assertEquals("validation", ctx.errors.firstOrNull()?.group)
	}

	@Test
	fun databaseError() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.UPDATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.DB_ERROR,
			commentRequest = MkplComment()
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("internal", ctx.errors.firstOrNull()?.group)
	}

	@Test
	fun badNoCase() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.UPDATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.NONE,
			commentRequest = MkplComment(
				id = id,
				entityId = entityId,
				comment = comment,
				rating = rating,
				entityType = entityType
			),
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("stub", ctx.errors.firstOrNull()?.field)
		assertEquals("validation", ctx.errors.firstOrNull()?.group)
	}
}
