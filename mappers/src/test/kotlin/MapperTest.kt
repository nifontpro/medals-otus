import org.junit.Test
import ru.otus.MkplContext
import ru.otus.api.v1.models.*
import ru.otus.model.*
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportComment
import ru.otus.stubs.MkplStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = CommentCreateRequest(
            requestId = "1234",
            debug = CommentDebug(
                mode = CommentRequestDebugMode.STUB,
                stub = CommentRequestDebugStubs.SUCCESS,
            ),
            comment = CommentCreateObject(
                entityId = "123",
                entityType = EntityType.AD,
                comment = "super",
                rating = 4
            ),
        )

        val context = MkplContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals("super", context.commentRequest.comment)
        assertEquals(MkplEntityId("123"), context.commentRequest.entityId)
        assertEquals(MkplEntityType.AD, context.commentRequest.entityType)
    }

    @Test
    fun toTransport() {
        val context = MkplContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            commentResponse = MkplComment(
                entityId = MkplEntityId("123"),
                entityType = MkplEntityType.AD,
                comment = "super",
                rating = 4
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MkplState.RUNNING,
        )

        val req = context.toTransportComment() as CommentCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("123", req.comment?.entityId)
        assertEquals("super", req.comment?.comment)
        assertEquals(EntityType.AD, req.comment?.entityType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
