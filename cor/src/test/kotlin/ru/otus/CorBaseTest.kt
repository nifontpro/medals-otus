package ru.otus

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.cor.handlers.CorWorker
import kotlin.test.assertEquals

class CorBaseTest {

	@Test
	fun `worker should execute handle`() = runBlocking {
		val worker = CorWorker<TestContext>(
			title = "w1",
			blockHandle = { history += "w1; " }
		)
		val ctx = TestContext()
		worker.exec(ctx)
		assertEquals("w1; ", ctx.history)
	}

}

/*private fun ICorChainDsl<TestContext>.printResult() = worker(title = "Print example") {
	println("some = $some")
}*/

data class TestContext(
	var status: CorStatuses = CorStatuses.NONE,
	var some: Int = 0,
	var history: String = "",
)

enum class CorStatuses {
	NONE,
	RUNNING,
	FAILING,
	DONE,
	ERROR
}