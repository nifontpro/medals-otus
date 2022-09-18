package validation

import ru.otus.bussines.CommentProcessor
import ru.otus.model.MkplCommand
import kotlin.test.Test

class BizValidationCreateTest {

	private val command = MkplCommand.CREATE
	private val processor by lazy { CommentProcessor() }

	@Test
	fun correctTitle() = validationCommentCorrect(command, processor)

	@Test
	fun trimComment() = validationCommentTrim(command, processor)

	@Test
	fun emptyComment() = validationTitleEmpty(command, processor)

	@Test
	fun badSymbolsTitle() = validationTitleSymbols(command, processor)

	@Test
	fun ratingCorrect() = validationRatingCorrect(command, processor)

	@Test
	fun badRating() = validationBadRating(command, processor)

}

