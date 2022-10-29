import ru.otus.inmemory.CommentRepoInMemory
import ru.otus.repo.ICommentRepository
import ru.otus.repo_tests.RepoCommentGetByIdTest

class CommentRepoInMemoryGetByIdTest : RepoCommentGetByIdTest() {
	override val repo: ICommentRepository = CommentRepoInMemory(
		initObjects = initObjects
	)
}
