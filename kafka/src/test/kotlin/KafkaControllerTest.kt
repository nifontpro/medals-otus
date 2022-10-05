import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.nifontbus.kafka.AppKafkaConfig
import ru.nifontbus.kafka.AppKafkaConsumer
import ru.nifontbus.kafka.ConsumerStrategyV1
import ru.otus.api.v1.models.*
import ru.otus.apiV1RequestSerialize
import ru.otus.apiV1ResponseDeserialize
import java.util.*
import kotlin.test.assertEquals


class KafkaControllerTest {
	@Test
	fun runKafka() {
		val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
		val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())

		val config = AppKafkaConfig()
//		val consumer: Consumer<String, String> = config.createKafkaConsumer()
//		val producer: Producer<String, String> = config.createKafkaProducer()

		val inputTopic = config.kafkaTopicInV1
		val outputTopic = config.kafkaTopicOutV1

		val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
		consumer.schedulePollTask {
			consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
			consumer.addRecord(
				ConsumerRecord(
					inputTopic,
					PARTITION,
					0L,
					"test-1",
					apiV1RequestSerialize(
						CommentCreateRequest(
							requestId = "11111111-1111-1111-1111-111111111111",
							comment = CommentCreateObject(
								entityId = "EA222",
								entityType = EntityType.AD,
								comment = "Ok",
								rating = 5
							),
							debug = CommentDebug(
								mode = CommentRequestDebugMode.STUB,
								stub = CommentRequestDebugStubs.SUCCESS
							)
						)
					)
				)
			)
			app.stop()
		}

		val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
		val tp = TopicPartition(inputTopic, PARTITION)
		startOffsets[tp] = 0L
		consumer.updateBeginningOffsets(startOffsets)

		app.run()

		val message = producer.history().first()
		val result = apiV1ResponseDeserialize<CommentCreateResponse>(message.value())
		assertEquals(outputTopic, message.topic())
		assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
		assertEquals("Ok", result.comment?.comment)
	}

	companion object {
		const val PARTITION = 0
	}
}


