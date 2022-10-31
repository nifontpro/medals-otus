package sql

import com.benasher44.uuid.uuid4
import org.testcontainers.containers.PostgreSQLContainer
import ru.otus.model.MkplComment
import ru.otus.postgree.RepoCommentSQL
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
	private const val USER = "postgres"
	private const val PASS = "marketplace-pass"
	private const val SCHEMA = "marketplace"

	private val container by lazy {
		PostgresContainer().apply {
			withUsername(USER)
			withPassword(PASS)
			withDatabaseName(SCHEMA)
			withStartupTimeout(Duration.ofSeconds(300L))
			start()
		}
	}

	private val url: String by lazy { container.jdbcUrl }

	fun repoUnderTestContainer(
		initObjects: Collection<MkplComment> = emptyList(),
		randomUuid: () -> String = { uuid4().toString() },
	): RepoCommentSQL {
		return RepoCommentSQL(url, USER, PASS, SCHEMA, initObjects, randomUuid = randomUuid)
	}
}
