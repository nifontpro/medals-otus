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
fun validationIdCorrect(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId(				id= "123-234-abc-ABC"),
			entityId = MkplEntityId("EA111"),
			comment = "test",
			rating = 5,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(0, ctx.errors.size)
	assertNotEquals(MkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId(" \n\t 123-234-abc-ABC \n\t "),
			entityId = MkplEntityId("EA111"),
			comment = "test",
			rating = 5,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(0, ctx.errors.size)
	assertNotEquals(MkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId(""),
			entityId = MkplEntityId("EA111"),
			comment = "test",
			rating = 5,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(1, ctx.errors.size)
	assertEquals(MkplState.FAILING, ctx.state)
	val error = ctx.errors.firstOrNull()
	assertEquals("id", error?.field)
	assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: MkplCommand, processor: CommentProcessor) = runTest {
	val ctx = MkplContext(
		command = command,
		state = MkplState.NONE,
		workMode = MkplWorkMode.TEST,
		commentRequest = MkplComment(
			id = MkplCommentId("!@#\$%^&*(),.{}"),
			entityId = MkplEntityId("EA111"),
			comment = "test",
			rating = 5,
			entityType = MkplEntityType.AD
		)
	)
	processor.exec(ctx)
	assertEquals(1, ctx.errors.size)
	assertEquals(MkplState.FAILING, ctx.state)
	val error = ctx.errors.firstOrNull()
	assertEquals("id", error?.field)
	assertContains(error?.message ?: "", "id")
}
