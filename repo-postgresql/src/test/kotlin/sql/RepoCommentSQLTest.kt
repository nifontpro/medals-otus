package sql

import ru.otus.repo.ICommentRepository
import ru.otus.repo_tests.RepoCommentCreateTest
import ru.otus.repo_tests.RepoCommentDeleteTest
import ru.otus.repo_tests.RepoCommentGetByIdTest
import ru.otus.repo_tests.RepoCommentUpdateTest

class RepoCommentSQLCreateTest : RepoCommentCreateTest() {
	override val repo: ICommentRepository = SqlTestCompanion.repoUnderTestContainer(
		initObjects,
		randomUuid = { lockNew.asString() },
	)
}

class RepoCommentSQLDeleteTest : RepoCommentDeleteTest() {
	override val repo: ICommentRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoCommentSQLGetByIdTest : RepoCommentGetByIdTest() {
	override val repo: ICommentRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoCommentSQLUpdateTest : RepoCommentUpdateTest() {
	override val repo: ICommentRepository = SqlTestCompanion.repoUnderTestContainer(
		initObjects,
		randomUuid = { lockNew.asString() },
	)
}
