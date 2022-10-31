import ru.otus.inmemory.CommentRepoInMemory
import ru.otus.repo_tests.RepoCommentCreateTest

class CommentRepoInMemoryCreateTest : RepoCommentCreateTest() {
	override val repo = CommentRepoInMemory(
		initObjects = initObjects,
		randomUuid = { lockNew.asString() }
	)
}
