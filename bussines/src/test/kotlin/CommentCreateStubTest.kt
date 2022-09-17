import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.MkplContext
import ru.otus.bussines.CommentProcessor
import ru.otus.model.*
import ru.otus.stubs.CommentStub
import ru.otus.stubs.MkplStubs
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CommentCreateStubTest {

	private val processor = CommentProcessor()
	private val entityId = MkplEntityId("EA111")
	private val comment = "Отличный товар для дома"
	private val rating = 5
	private val entityType = MkplEntityType.AD

	@Test
	fun create() = runTest {

		val ctx = MkplContext(
			command = MkplCommand.CREATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.SUCCESS,
			commentRequest = MkplComment(
				entityId = entityId,
				comment = comment,
				rating = rating,
				entityType = entityType
			),
		)
		processor.exec(ctx)
		assertEquals(CommentStub.get().entityId, ctx.commentResponse.entityId)
		assertEquals(CommentStub.get().comment, ctx.commentResponse.comment)
		assertEquals(CommentStub.get().rating, ctx.commentResponse.rating)
		assertEquals(CommentStub.get().entityType, ctx.commentResponse.entityType)
	}

	@Test
	fun badComment() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.CREATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.BAD_COMMENT,
			commentRequest = MkplComment(
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
			command = MkplCommand.CREATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.BAD_RATING,
			commentRequest = MkplComment(
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
			command = MkplCommand.CREATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.DB_ERROR,
			commentRequest = MkplComment(
				entityId = entityId,
			)
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("internal", ctx.errors.firstOrNull()?.group)
	}

	@Test
	fun badNoCase() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.CREATE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.NONE,
			commentRequest = MkplComment(
				entityId = entityId,
				comment = comment,
				rating = rating,
				entityType = entityType
			)
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("stub", ctx.errors.firstOrNull()?.field)
		assertEquals("validation", ctx.errors.firstOrNull()?.group)
	}
}