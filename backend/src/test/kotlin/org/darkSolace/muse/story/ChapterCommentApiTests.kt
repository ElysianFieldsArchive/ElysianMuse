package org.darkSolace.muse.story

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.story.model.dto.ChapterCommentDTO
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.service.StoryChapterService
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

class ChapterCommentApiTests(
    @Autowired private val userService: UserService,
    @Autowired private val userRoleService: UserRoleService,
    @Autowired private val mailService: MailService,
    @Autowired private val storyService: StoryService,
    @Autowired private val storyChapterService: StoryChapterService,
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
    fun addChapterComment() {
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

        var url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(commentDTO, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(2)
    fun addChapterComment_invalidChapter() {
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

        var url = generateUrl("/api/story/chapter/-1/comment")

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(commentDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(3)
    fun addChapterComment_invalidComment() {
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

        var url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val commentDTO = ChapterCommentDTO()

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(commentDTO, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(4)
    fun addChapterComment_invalidAuthor() {
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

        var url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user3)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(commentDTO, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(5)
    fun addChapterComment_invalidUser() {
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

        var url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(commentDTO, null))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(6)
    fun editChapterComment() {
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

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val success = storyChapterService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val editedCommentDTO = ChapterCommentDTO().also {
            it.id = chapterCommentDTOs?.first()?.id
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Edited Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedCommentDTO, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
        Assertions.assertEquals("Edited Comment", retrievedStory.body?.chapters?.first()?.comments?.first()?.content)
    }

    @Test
    @Order(7)
    fun editChapterComment_invalidChapter() {
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

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val success = storyChapterService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        url = generateUrl("/api/story/chapter/-1/comment")

        val editedCommentDTO = ChapterCommentDTO().also {
            it.id = chapterCommentDTOs?.first()?.id
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = -1
            it.content = "Edited Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedCommentDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
        Assertions.assertEquals("Test Comment", retrievedStory.body?.chapters?.first()?.comments?.first()?.content)
    }

    @Test
    @Order(8)
    fun editChapterComment_invalidComment() {
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

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val success = storyChapterService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")


        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val editedCommentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedCommentDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
        Assertions.assertEquals("Test Comment", retrievedStory.body?.chapters?.first()?.comments?.first()?.content)
    }

    @Test
    @Order(9)
    fun editChapterComment_invalidAuthor() {
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

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val success = storyChapterService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val editedCommentDTO = ChapterCommentDTO().also {
            it.id = chapterCommentDTOs?.first()?.id
            it.author = UserIdNameDTO.from(user3)
            it.chapterId = story.chapters.first().id
            it.content = "Edited Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedCommentDTO, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
        Assertions.assertEquals("Test Comment", retrievedStory.body?.chapters?.first()?.comments?.first()?.content)
    }

    @Test
    @Order(10)
    fun editChapterComment_invalidUser() {
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

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val success = storyChapterService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val editedCommentDTO = ChapterCommentDTO().also {
            it.id = chapterCommentDTOs?.first()?.id
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Edited Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedCommentDTO, null))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
        Assertions.assertEquals("Test Comment", retrievedStory.body?.chapters?.first()?.comments?.first()?.content)
    }

    @Test
    @Order(11)
    fun editChapterComment_asAdmin() {
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

        val commentDTO = ChapterCommentDTO().also {
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val success = storyChapterService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        userRoleService.changeRole(user3, Role.ADMINISTRATOR)

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user3", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        url = generateUrl("/api/story/chapter/${story.chapters.first().id}/comment")

        val editedCommentDTO = ChapterCommentDTO().also {
            it.id = chapterCommentDTOs?.first()?.id
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = story.chapters.first().id
            it.content = "Edited Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        val response = restTemplate.exchange<String>(url, HttpMethod.POST, HttpEntity(editedCommentDTO, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
        Assertions.assertEquals("Edited Comment", retrievedStory.body?.chapters?.first()?.comments?.first()?.content)
    }
}
