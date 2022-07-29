import org.junit.Test
import ru.otus.mappers.toMedal
import ru.otus.mappers.toMedalDto
import ru.otus.mappers.toUser
import ru.otus.mappers.toUserDto
import ru.otus.model.*
import kotlin.test.assertEquals

class TestMappers {

    @Test
    fun `Mapping User to UserDto`() {
        val user = buildUser { name = "Ivan"; email = "ivan@test.ru"; id = "1" }
        val userDto = user.toUserDto()
        assertEquals(UserDto(name = "Ivan", email = "ivan@test.ru", id = "1"), userDto)
    }

    @Test
    fun `Mapping UserDto to User`() {
        val userDto = UserDto(name = "Max", email = "max@test.ru", id = "2")
        val user = userDto.toUser()
        assertEquals(User(name = "Max", email = "max@test.ru", id = "2"), user)
    }

    @Test
    fun `Mapping Medal to MedalDto`() {
        val medal = Medal(name = "За труды", userId = "1", score = 4, id = "1")
        val medalDto = medal.toMedalDto()
        val expMedalDto = MedalDto(name = "За труды", userId = "1", score = 4, id = "1")
        assertEquals(expMedalDto, medalDto)
    }

    @Test
    fun `Mapping MedalDto to Medal`() {
        val medalDto = MedalDto(name = "За отвагу", userId = "2", score = 4, id = "2")
        val medal = medalDto.toMedal()
        val excMedal = Medal(name = "За отвагу", userId = "2", score = 4, id = "2")
        assertEquals(excMedal, medal)
    }
}