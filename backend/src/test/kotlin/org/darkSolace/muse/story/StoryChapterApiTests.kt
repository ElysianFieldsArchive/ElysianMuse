package org.darkSolace.muse.story

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.service.StoryService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
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

class StoryChapterApiTests(
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
    fun addChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
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

        val chapterDTO = ChapterDTO().also {
            it.storyId = story.id
            it.title = "Chapter Title"
            it.content = "Chapter Content"
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(chapterDTO, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.size)
        Assertions.assertEquals("Chapter Title", retrievedStory.body?.chapters?.first()?.title)
        Assertions.assertEquals("Chapter Content", retrievedStory.body?.chapters?.first()?.content)
    }

    @Test
    @Order(2)
    fun addChapter_invalidStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
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

        val chapterDTO = ChapterDTO().also {
            it.storyId = story.id
            it.title = "Chapter Title"
            it.content = "Chapter Content"
        }

        var url = generateUrl("/api/story/-1")
        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(chapterDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Story not found", response.body)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.size)
    }

    @Test
    @Order(3)
    fun addChapter_invalidChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
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

        val chapterDTO = ChapterDTO().also {
            it.storyId = -1
            it.title = "Chapter Title"
            it.content = "Chapter Content"
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(chapterDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Chapter does not match story", response.body)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.size)
    }

    @Test
    @Order(4)
    fun addChapter_invalidChapterNoContent() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
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

        val chapterDTO = ChapterDTO().also {
            it.storyId = story.id
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(chapterDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.size)
    }

    @Test
    @Order(5)
    fun addChapter_notAuthor() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
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

        val chapterDTO = ChapterDTO().also {
            it.storyId = story.id
            it.title = "Chapter Title"
            it.content = "Chapter Content"
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(chapterDTO, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals("User is not an author", response.body)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.size)
    }

    @Test
    @Order(6)
    fun editChapter() {
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

        val editedChapter = ChapterDTO().also {
            it.storyId = story.id
            it.id = story.chapters.first().id
            it.title = "Edited Title"
            it.content = "Edited Content"
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedChapter, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.size)
        Assertions.assertEquals("Edited Title", retrievedStory.body?.chapters?.first()?.title)
        Assertions.assertEquals("Edited Content", retrievedStory.body?.chapters?.first()?.content)
    }

    @Test
    @Order(7)
    fun editChapter_invalidChapter() {
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

        val editedChapter = ChapterDTO().also {
            it.storyId = story.id
            it.id = -1
            it.title = "Edited Title"
            it.content = "Edited Content"
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedChapter, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Chapter not in Story", response.body)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.size)
        Assertions.assertEquals("Chapter Title", retrievedStory.body?.chapters?.first()?.title)
        Assertions.assertEquals("Chapter Content", retrievedStory.body?.chapters?.first()?.content)
    }

    @Test
    @Order(8)
    fun editChapter_wrongAuthor() {
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

        val editedChapter = ChapterDTO().also {
            it.storyId = story.id
            it.id = story.chapters.first().id
            it.title = "Edited Title"
            it.content = "Edited Content"
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedChapter, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals("User is not an author", response.body)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.size)
        Assertions.assertEquals("Chapter Title", retrievedStory.body?.chapters?.first()?.title)
        Assertions.assertEquals("Chapter Content", retrievedStory.body?.chapters?.first()?.content)
    }

    @Test
    @Order(9)
    fun editChapter_Admin() {
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

        val editedChapter = ChapterDTO().also {
            it.storyId = story.id
            it.id = story.chapters.first().id
            it.title = "Edited Title"
            it.content = "Edited Content"
        }

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedChapter, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.size)
        Assertions.assertEquals("Edited Title", retrievedStory.body?.chapters?.first()?.title)
        Assertions.assertEquals("Edited Content", retrievedStory.body?.chapters?.first()?.content)
    }

    @Test
    @Order(10)
    fun deleteChapter() {
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

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.size)
    }

    @Test
    @Order(11)
    fun deleteChapter_invalidChapter() {
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

        var url = generateUrl("/api/story/${story.id}/-1")
        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.size)
    }

    @Test
    @Order(12)
    fun deleteChapter_wrongAuthor() {
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

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.size)
    }

    @Test
    @Order(13)
    fun deleteChapter_Admin() {
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

        var url = generateUrl("/api/story/${story.id}/${story.chapters.first().id}")
        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.size)
    }
}
