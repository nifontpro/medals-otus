package ru.otus.repo_tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.model.*
import ru.otus.repo.DbCommentRequest
import ru.otus.repo.ICommentRepository
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommentCreateTest {
	abstract val repo: ICommentRepository

	protected open val lockNew: MkplLock = MkplLock("20000000-0000-0000-0000-000000000002")

	private val createObj = MkplComment(
		comment = "create object",
		rating = 5,
		entityId = MkplEntityId("entity-123"),
		entityType = MkplEntityType.AD,
	)

	@Test
	fun createSuccess() = runTest {
		val result = repo.createComment(DbCommentRequest(createObj))
		val expected = createObj.copy(id = result.data?.id ?: MkplCommentId.NONE)
		assertEquals(true, result.isSuccess)
		assertEquals(expected.comment, result.data?.comment)
		assertEquals(expected.rating, result.data?.rating)
		assertEquals(expected.entityType, result.data?.entityType)
		assertNotEquals(MkplCommentId.NONE, result.data?.id)
		assertEquals(emptyList(), result.errors)
		assertEquals(lockNew, result.data?.lock)
	}

	companion object : BaseInitComments("create") {
		override val initObjects: List<MkplComment> = emptyList()
	}
}
