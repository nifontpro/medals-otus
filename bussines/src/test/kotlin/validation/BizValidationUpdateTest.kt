package validation

import ru.otus.bussines.CommentProcessor
import ru.otus.model.MkplCommand
import kotlin.test.Test

class BizValidationUpdateTest {

	private val command = MkplCommand.UPDATE
	private val processor by lazy { CommentProcessor() }

	@Test
	fun correctComment() = validationCommentCorrect(command, processor)

	@Test
	fun trimComment() = validationCommentTrim(command, processor)

	@Test
	fun emptyComment() = validationTitleEmpty(command, processor)

	@Test
	fun badSymbolsComment() = validationTitleSymbols(command, processor)

	@Test
	fun correctRating() = validationRatingCorrect(command, processor)

	@Test
	fun badRating() = validationBadRating(command, processor)


	@Test
	fun correctId() = validationIdCorrect(command, processor)

	@Test
	fun trimId() = validationIdTrim(command, processor)

	@Test
	fun emptyId() = validationIdEmpty(command, processor)

	@Test
	fun badFormatId() = validationIdFormat(command, processor)

}

