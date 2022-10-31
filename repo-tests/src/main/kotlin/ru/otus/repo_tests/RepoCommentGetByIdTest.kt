package ru.otus.repo_tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.model.MkplComment
import ru.otus.model.MkplCommentId
import ru.otus.repo.DbCommentIdRequest
import ru.otus.repo.ICommentRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommentGetByIdTest {
	abstract val repo: ICommentRepository

	@Test
	fun readSuccess() = runTest {
		val result = repo.getByIdComment(DbCommentIdRequest(successId))

		assertEquals(true, result.isSuccess)
		assertEquals(readSuccessStub, result.data)
		assertEquals(emptyList(), result.errors)
	}

	@Test
	fun readNotFound() = runTest {
		val result = repo.getByIdComment(DbCommentIdRequest(notFoundId))

		assertEquals(false, result.isSuccess)
		assertEquals(null, result.data)
		val error = result.errors.find { it.code == "not-found" }
		assertEquals("id", error?.field)
	}

	companion object : BaseInitComments("delete") {
		override val initObjects: List<MkplComment> = listOf(
			createInitTestModel("read")
		)
		private val readSuccessStub = initObjects.first()

		val successId = MkplCommentId(readSuccessStub.id.asString())
		val notFoundId = MkplCommentId("comment-repo-read-notFound")

	}
}
