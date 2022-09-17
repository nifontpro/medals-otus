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
class CommentDeleteStubTest {

	private val processor = CommentProcessor()
	private val id = MkplCommentId("CA1")

	@Test
	fun delete() = runTest {

		val ctx = MkplContext(
			command = MkplCommand.DELETE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.SUCCESS,
			commentRequest = MkplComment(
				id = id,
			)
		)
		processor.exec(ctx)

		val stub = CommentStub.get()
		assertEquals(stub.id, ctx.commentResponse.id)
		assertEquals(stub.comment, ctx.commentResponse.comment)
		assertEquals(stub.rating, ctx.commentResponse.rating)
		assertEquals(stub.entityType, ctx.commentResponse.entityType)
	}

	@Test
	fun badId() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.DELETE,
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
	fun databaseError() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.DELETE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.DB_ERROR,
			commentRequest = MkplComment(
				id = id,
			)
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("internal", ctx.errors.firstOrNull()?.group)
	}

	@Test
	fun badNoCase() = runTest {
		val ctx = MkplContext(
			command = MkplCommand.DELETE,
			state = MkplState.NONE,
			workMode = MkplWorkMode.STUB,
			stubCase = MkplStubs.NONE,
			commentRequest = MkplComment(
				id = id,
			)
		)
		processor.exec(ctx)
		assertEquals(MkplComment(), ctx.commentResponse)
		assertEquals("stub", ctx.errors.firstOrNull()?.field)
	}
}
