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
fun validationCommentCorrect(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId("C333"),
			entityId = MkplEntityId("EA111"),
			comment = "test",
			rating = 5,
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
fun validationCommentTrim(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId("C333"),
			entityId = MkplEntityId("EA111"),
			comment = " \n\t test \t\n ",
			rating = 5,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(0, ctx.errors.size)
	assertNotEquals(MkplState.FAILING, ctx.state)
	assertEquals("test", ctx.commentValidated.comment)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId("C333"),
			entityId = MkplEntityId("EA111"),
			comment = "   ",
			rating = 5,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(1, ctx.errors.size)
	assertEquals(MkplState.FAILING, ctx.state)
	val error = ctx.errors.firstOrNull()
	assertEquals("comment", error?.field)
	assertContains(error?.message ?: "", "comment")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId("C333"),
			entityId = MkplEntityId("EA111"),
			comment = "!@#\$%^&*(),.{} ",
			rating = 5,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(1, ctx.errors.size)
	assertEquals(MkplState.FAILING, ctx.state)
	val error = ctx.errors.firstOrNull()
	assertEquals("comment", error?.field)
	assertContains(error?.message ?: "", "comment")
}
