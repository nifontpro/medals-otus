import ru.otus.api.v1.models.*
import ru.otus.apiV1Mapper
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = CommentCreateRequest(
        requestId = "123",
        debug = CommentDebug(
            mode = CommentRequestDebugMode.STUB,
            stub = CommentRequestDebugStubs.BAD_RATING
        ),
        comment = BaseComment(
            entityId = "id_entity",
            entityType = EntityType.AD,
            comment = "Текст комментария",
            rating = 5,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"entityId\":\\s*\"id_entity\""))
        assertContains(json, Regex("\"comment\":\\s*\"Текст комментария\""))
        assertContains(json, Regex("\"entityType\":\\s*\"ad\""))
        assertContains(json, Regex("\"stub\":\\s*\"badRating\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as CommentCreateRequest

        assertEquals(request, obj)
    }
}
