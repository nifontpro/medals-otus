import ru.otus.api.v1.models.*
import ru.otus.apiV1Mapper
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = CommentCreateResponse(
        requestId = "123",
        comment = BaseComment(
            entityId = "id_entity",
            entityType = EntityType.AD,
            comment = "Текст комментария",
            rating = 5,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"comment\":\\s*\"Текст комментария\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as CommentCreateResponse

        assertEquals(response, obj)
    }
}
