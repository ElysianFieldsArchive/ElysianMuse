package org.darkSolace.muse.story

import org.apache.hc.core5.http.ContentType
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryChapterContributorDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.service.StoryService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.util.*

class ChapterContributorApiTests(
    @Autowired private val userService: UserService,
    @Autowired private val userRoleService: UserRoleService,
    @Autowired private val mailService: MailService,
    @Autowired private val storyService: StoryService,
    @Autowired private val storyRepository: StoryRepository,
) : TestBase() {
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
        user1 = userService.createUserFromSignUpRequest(SignUpRequest("user1", "123456", "user1@example.com"))
            ?: fail("Coundn't create User")
        user2 = userService.createUserFromSignUpRequest(SignUpRequest("user2", "123456", "user2@example.com"))
            ?: fail("Coundn't create User")
        user3 = userService.createUserFromSignUpRequest(SignUpRequest("user3", "123456", "user3@example.com"))
            ?: fail("Coundn't create User")
        mailService.markEMailAsValid("user1")
        mailService.markEMailAsValid("user2")
        mailService.markEMailAsValid("user3")
    }

    @Test
    @Order(1)
    fun addContributorToChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.BETA)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.ARTIST)

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(HttpStatus.OK, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(2)
    fun addContributorToChapter_invalidStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/-1/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.BETA)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.ARTIST)

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(3)
    fun addContributorToChapter_invalidChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/-1/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.BETA)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.ARTIST)

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(4)
    fun addContributorToChapter_invalidTag() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
            add("Content-Type", ContentType.APPLICATION_JSON.mimeType)
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO =
            """{ "user" : { "id": ${user2.id}, "username" : "${user2.username}" }, "userTag" : "RANDO" }"""

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(5)
    fun addContributorToChapter_invalidUser() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
            add("Content-Type", ContentType.APPLICATION_JSON.mimeType)
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = """{ "user" : null, "userTag" : "BETA" }"""

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(6)
    fun addContributorToChapter_notAuthor() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.BETA)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.ARTIST)

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(7)
    fun addContributorToChapter_asAdmin() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        userRoleService.changeRole(user2, Role.ADMINISTRATOR)

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.BETA)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.ARTIST)

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(HttpStatus.OK, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(8)
    fun removeContributorFromChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
                this.artist = setOf(UserIdNameDTO.from(user2))
                this.beta = setOf(UserIdNameDTO.from(user3))
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.ARTIST)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.BETA)

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(HttpStatus.OK, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(9)
    fun removeContributorFromChapter_invalidStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
                this.artist = setOf(UserIdNameDTO.from(user2))
                this.beta = setOf(UserIdNameDTO.from(user3))
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/-1/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.ARTIST)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.BETA)

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(10)
    fun removeContributorFromChapter_invalidChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
                this.artist = setOf(UserIdNameDTO.from(user2))
                this.beta = setOf(UserIdNameDTO.from(user3))
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/-1/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.ARTIST)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.BETA)

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(11)
    fun removeContributorFromChapter_invalidTag() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
                this.artist = setOf(UserIdNameDTO.from(user2))
                this.beta = setOf(UserIdNameDTO.from(user3))
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
            add("Content-Type", ContentType.APPLICATION_JSON.mimeType)
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO =
            """{ "user" : { "id": ${user2.id}, "username" : "${user2.username}" }, "userTag" : "RANDO" }"""

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(12)
    fun removeContributorFromChapter_invalidUser() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
                this.artist = setOf(UserIdNameDTO.from(user2))
                this.beta = setOf(UserIdNameDTO.from(user3))
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
            add("Content-Type", ContentType.APPLICATION_JSON.mimeType)
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = """{ "user" : null, "userTag" : "BETA" }"""

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(13)
    fun removeContributorFromChapter_notAuthor() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
                this.artist = setOf(UserIdNameDTO.from(user2))
                this.beta = setOf(UserIdNameDTO.from(user3))
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.ARTIST)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.BETA)

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.beta?.size)
    }

    @Test
    @Order(14)
    fun removeContributorFromChapter_asAdmin() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
            storyDto.chapters = listOf(ChapterDTO().apply {
                this.title = "Chapter Title"
                this.content = "Chapter Content"
                this.artist = setOf(UserIdNameDTO.from(user2))
                this.beta = setOf(UserIdNameDTO.from(user3))
            })
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found!")

        userRoleService.changeRole(user2, Role.ADMINISTRATOR)

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}/contributor")
        val storyContributorDTO = StoryChapterContributorDTO(UserIdNameDTO.from(user2), UserTag.ARTIST)
        val storyContributorDTO2 = StoryChapterContributorDTO(UserIdNameDTO.from(user3), UserTag.BETA)

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))
        val response2 = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO2, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(HttpStatus.OK, response2.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.beta?.size)
    }
}
