package validation

import ru.otus.bussines.CommentProcessor
import ru.otus.model.MkplCommand
import kotlin.test.Test

class BizValidationDeleteTest {

	private val command = MkplCommand.DELETE
	private val processor by lazy { CommentProcessor() }

	@Test
	fun correctId() = validationIdCorrect(command, processor)

	@Test
	fun trimId() = validationIdTrim(command, processor)

	@Test
	fun emptyId() = validationIdEmpty(command, processor)

	@Test
	fun badFormatId() = validationIdFormat(command, processor)

}

