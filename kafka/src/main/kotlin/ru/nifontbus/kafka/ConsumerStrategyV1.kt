package ru.nifontbus.kafka

import ru.otus.MkplContext
import ru.otus.api.v1.models.IRequest
import ru.otus.api.v1.models.IResponse
import ru.otus.apiV1RequestDeserialize
import ru.otus.apiV1ResponseSerialize
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportComment

class ConsumerStrategyV1 : ConsumerStrategy {
	override fun topics(config: AppKafkaConfig): InputOutputTopics {
		return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
	}

	override fun serialize(source: MkplContext): String {
		val response: IResponse = source.toTransportComment()
		return apiV1ResponseSerialize(response)
	}

	override fun deserialize(value: String, target: MkplContext) {
		val request: IRequest = apiV1RequestDeserialize(value)
		target.fromTransport(request)
	}
}