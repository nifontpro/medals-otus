import ru.otus.inmemory.CommentRepoInMemory
import ru.otus.repo.ICommentRepository
import ru.otus.repo_tests.RepoCommentUpdateTest

class CommentRepoInMemoryUpdateTest : RepoCommentUpdateTest() {
	override val repo: ICommentRepository = CommentRepoInMemory(
		initObjects = initObjects,
		randomUuid = { lockNew.asString() }
	)
}
