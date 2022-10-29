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
abstract class RepoCommentDeleteTest {
	abstract val repo: ICommentRepository

	@Test
	fun deleteSuccess() = runTest {
		val result = repo.deleteComment(DbCommentIdRequest(successId, lock = lockOld))

		assertEquals(true, result.isSuccess)
		assertEquals(emptyList(), result.errors)
		assertEquals(lockOld, result.data?.lock)
	}

	@Test
	fun deleteNotFound() = runTest {
		val result = repo.getByIdComment(DbCommentIdRequest(notFoundId, lock = lockOld))

		assertEquals(false, result.isSuccess)
		assertEquals(null, result.data)
		val error = result.errors.find { it.code == "not-found" }
		assertEquals("id", error?.field)
	}

	@Test
	fun deleteConcurrency() = runTest {
		val result = repo.deleteComment(DbCommentIdRequest(concurrencyId, lock = lockBad))

		assertEquals(false, result.isSuccess)
		val error = result.errors.find { it.code == "concurrency" }
		assertEquals("lock", error?.field)
		assertEquals(lockOld, result.data?.lock)
	}

	companion object : BaseInitComments("delete") {
		override val initObjects: List<MkplComment> = listOf(
			createInitTestModel("delete"),
			createInitTestModel("deleteLock"),
		)
		val successId = MkplCommentId(initObjects[0].id.asString())
		val notFoundId = MkplCommentId("ad-repo-delete-notFound")
		val concurrencyId = MkplCommentId(initObjects[1].id.asString())
	}
}
