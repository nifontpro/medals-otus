package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.MkplContext
import ru.otus.bussines.CommentProcessor
import ru.otus.model.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationRatingCorrect(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId("C333"),
			entityId = MkplEntityId("EA111"),
			comment = "test",
			rating = 3,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	println("ERRORS: " + ctx.errors)
	assertEquals(0, ctx.errors.size)
	assertNotEquals(MkplState.FAILING, ctx.state)
	assertEquals("test", ctx.commentValidated.comment)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationBadRating(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId("C333"),
			entityId = MkplEntityId("EA111"),
			comment = "test",
			rating = 33,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(1, ctx.errors.size)
	assertEquals(MkplState.FAILING, ctx.state)
	val error = ctx.errors.firstOrNull()
	assertEquals("rating", error?.field)
	assertContains(error?.message ?: "", "rating")
}