import ru.otus.inmemory.CommentRepoInMemory
import ru.otus.repo.ICommentRepository
import ru.otus.repo_tests.RepoCommentDeleteTest

class CommentRepoInMemoryDeleteTest : RepoCommentDeleteTest() {
    override val repo: ICommentRepository = CommentRepoInMemory(
        initObjects = initObjects
    )
}
