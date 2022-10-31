package ru.otus.repo_tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.model.*
import ru.otus.repo.DbCommentRequest
import ru.otus.repo.ICommentRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommentUpdateTest {
    abstract val repo: ICommentRepository
    protected val updateSucc = initObjects[0]
    protected val updateConc = initObjects[1]
    protected val updateIdNotFound = MkplCommentId("comment-repo-update-not-found")
    protected val lockBad = MkplLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = MkplLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc = MkplComment(
        id = updateSucc.id,
        comment = "update object",
        rating = 5,
        entityId = MkplEntityId("entity-123"),
        entityType = MkplEntityType.AD,
        lock = initObjects.first().lock,
    )
    private val reqUpdateNotFound = MkplComment(
        id = updateIdNotFound,
        comment = "update object not found",
        rating = 5,
        entityId = MkplEntityId("entity-123"),
        entityType = MkplEntityType.AD,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc = MkplComment(
        id = updateConc.id,
        comment = "update object not found",
        rating = 5,
        entityId = MkplEntityId("entity-123"),
        entityType = MkplEntityType.AD,
        lock = lockBad,
    )

    @Test
    fun updateSuccess() = runTest {
        val result = repo.updateComment(DbCommentRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.comment, result.data?.comment)
        assertEquals(reqUpdateSucc.rating, result.data?.rating)
        assertEquals(reqUpdateSucc.entityType, result.data?.entityType)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runTest {
        val result = repo.updateComment(DbCommentRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runTest {
        val result = repo.updateComment(DbCommentRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitComments("update") {
        override val initObjects: List<MkplComment> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
