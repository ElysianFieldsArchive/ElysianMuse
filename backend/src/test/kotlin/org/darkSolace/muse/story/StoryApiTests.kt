package org.darkSolace.muse.story

import org.apache.hc.core5.http.ContentType
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.story.model.Rating
import org.darkSolace.muse.story.model.StoryTag
import org.darkSolace.muse.story.model.StoryTagType
import org.darkSolace.muse.story.model.dto.ChapterCommentDTO
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryChapterContributorDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.model.dto.UserContributionDTO
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

class StoryApiTests(
    @Autowired val userService: UserService,
    @Autowired val userRoleService: UserRoleService,
    @Autowired val mailService: MailService,
    @Autowired val storyService: StoryService,
    @Autowired val storyRepository: StoryRepository,
) : TestBase() {

    lateinit var user1: User
    lateinit var user2: User
    lateinit var user3: User

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
    fun getStoryById() {
        storyService.createStory(StoryDTO().also {
            it.author = setOf(UserIdNameDTO.from(user1))
            it.title = "Test Title"
        })

        val story = storyService.getAllStories().firstOrNull() ?: fail("Story not created")

        val url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)


        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertNotNull(retrievedStory.body)
        Assertions.assertEquals(story.id, retrievedStory.body?.id)
    }

    @Test
    @Order(2)
    fun getStoryById_invalid() {
        val url = generateUrl("/api/story/5000")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)


        Assertions.assertEquals(HttpStatus.NOT_FOUND, retrievedStory.statusCode)
        Assertions.assertNull(retrievedStory.body)
    }

    @Test
    @Order(3)
    fun getAllStories() {
        val url = generateUrl("/api/story/all")
        var retrievedStory = restTemplate.getForEntity(url, Array<StoryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertNotNull(retrievedStory.body)
        Assertions.assertEquals(0, retrievedStory.body?.size)

        storyService.createStory(StoryDTO().also {
            it.author = setOf(UserIdNameDTO.from(user1))
            it.title = "Test Title"
        })
        storyService.createStory(StoryDTO().also {
            it.author = setOf(UserIdNameDTO.from(user2))
            it.title = "Test Title"
        })
        storyService.createStory(StoryDTO().also {
            it.author = setOf(UserIdNameDTO.from(user3))
            it.title = "Test Title"
        })


        retrievedStory = restTemplate.getForEntity(url, Array<StoryDTO>::class.java)


        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertNotNull(retrievedStory.body)
        Assertions.assertEquals(3, retrievedStory.body?.size)
    }

    @Test
    @Order(4)
    fun getStoriesFiltered_emptyFilter() {
        storyService.createStory(StoryDTO().also {
            it.author = setOf(UserIdNameDTO.from(user1))
            it.title = "Test Title"
        })
        storyService.createStory(StoryDTO().also {
            it.author = setOf(UserIdNameDTO.from(user2))
            it.title = "Test Title"
        })
        storyService.createStory(StoryDTO().also {
            it.author = setOf(UserIdNameDTO.from(user3))
            it.title = "Test Title"
        })

        val url = generateUrl("/api/story/filtered")
        val retrievedStory = restTemplate.postForEntity(url, mapOf<String, String>(), Array<StoryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertNotNull(retrievedStory.body)
        Assertions.assertEquals(3, retrievedStory.body?.size)
    }

    @Test
    @Order(5)
    fun getStoriesFiltered_ratingFilter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
            storyDto.rating = Rating.PARENTAL_GUIDANCE
        }
        val storyDTO2 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
            storyDto.rating = Rating.PARENTAL_GUIDANCE
        }
        val storyDTO3 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
            storyDto.rating = Rating.NC_17
        }

        storyService.createStory(storyDTO)
        storyService.createStory(storyDTO2)
        storyService.createStory(storyDTO3)

        val url = generateUrl("/api/story/filtered")
        val storiesAll = restTemplate.postForEntity(
            url, mapOf("ratings" to listOf(Rating.PARENTAL_GUIDANCE, Rating.NC_17)), Array<StoryDTO>::class.java
        )
        val storiesPG = restTemplate.postForEntity(
            url, mapOf("ratings" to listOf(Rating.PARENTAL_GUIDANCE)), Array<StoryDTO>::class.java
        )
        val storiesNC =
            restTemplate.postForEntity(url, mapOf("ratings" to listOf(Rating.NC_17)), Array<StoryDTO>::class.java)
        val storiesAdult =
            restTemplate.postForEntity(url, mapOf("ratings" to listOf(Rating.ADULT_ONLY)), Array<StoryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, storiesAll.statusCode)
        Assertions.assertEquals(3, storiesAll.body?.size)

        Assertions.assertEquals(HttpStatus.OK, storiesPG.statusCode)
        Assertions.assertEquals(2, storiesPG.body?.size)

        Assertions.assertEquals(HttpStatus.OK, storiesNC.statusCode)
        Assertions.assertEquals(1, storiesNC.body?.size)

        Assertions.assertEquals(HttpStatus.OK, storiesAdult.statusCode)
        Assertions.assertEquals(0, storiesAdult.body?.size)
    }

    @Test
    @Order(6)
    fun getStoriesFiltered_tagFilter() {
        val tag1 = storyService.createStoryTag(StoryTag(null, "Tommy", "Tommy Test", StoryTagType.CHARACTER))
        val tag2 = storyService.createStoryTag(StoryTag(null, "Short", "Short Reads", StoryTagType.CATEGORY))
        val tag3 = storyService.createStoryTag(StoryTag(null, "Fantasy", "Fantasy", StoryTagType.GENRE))
        val tag4 = storyService.createStoryTag(StoryTag(null, "Socks", "Wet Socks", StoryTagType.WARNING))

        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
            storyDto.storyTags = setOf(tag1, tag2)
        }
        val storyDTO2 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.storyTags = setOf(tag2, tag3)
        }
        val storyDTO3 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
            storyDto.storyTags = setOf(tag3, tag4)
        }

        storyService.createStory(storyDTO)
        storyService.createStory(storyDTO2)
        storyService.createStory(storyDTO3)

        val url = generateUrl("/api/story/filtered")
        val storyChar = restTemplate.postForEntity(url, mapOf("tags" to listOf(tag1)), Array<StoryDTO>::class.java)
        val storyCat = restTemplate.postForEntity(url, mapOf("tags" to listOf(tag2)), Array<StoryDTO>::class.java)
        val storyGenre = restTemplate.postForEntity(url, mapOf("tags" to listOf(tag3)), Array<StoryDTO>::class.java)
        val storyWarn = restTemplate.postForEntity(url, mapOf("tags" to listOf(tag4)), Array<StoryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, storyChar.statusCode)
        Assertions.assertEquals(1, storyChar.body?.size)
        Assertions.assertTrue(storyChar.body?.all { it.storyTags.any { it.type == StoryTagType.CHARACTER } } ?: false)

        Assertions.assertEquals(HttpStatus.OK, storyCat.statusCode)
        Assertions.assertEquals(2, storyCat.body?.size)
        Assertions.assertTrue(storyCat.body?.all { it.storyTags.any { it.type == StoryTagType.CATEGORY } } ?: false)

        Assertions.assertEquals(HttpStatus.OK, storyGenre.statusCode)
        Assertions.assertEquals(2, storyGenre.body?.size)
        Assertions.assertTrue(storyGenre.body?.all { it.storyTags.any { it.type == StoryTagType.GENRE } } ?: false)

        Assertions.assertEquals(HttpStatus.OK, storyWarn.statusCode)
        Assertions.assertEquals(1, storyWarn.body?.size)
        Assertions.assertTrue(storyWarn.body?.all { it.storyTags.any { it.type == StoryTagType.WARNING } } ?: false)
    }

    @Test
    @Order(7)
    fun getStoriesFiltered_userFilter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }
        val storyDTO2 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2))
        }
        val storyDTO3 = StoryDTO().also { storyDto ->
            storyDto.author =
                mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2), UserIdNameDTO.from(user3))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)
        storyService.createStory(storyDTO2)
        storyService.createStory(storyDTO3)

        val url = generateUrl("/api/story/filtered")
        val storiesUser1 =
            restTemplate.postForEntity(url, mapOf("authors" to listOf(user1)), Array<StoryDTO>::class.java)
        val storiesUser2 =
            restTemplate.postForEntity(url, mapOf("authors" to listOf(user2)), Array<StoryDTO>::class.java)
        val storiesUser3 =
            restTemplate.postForEntity(url, mapOf("authors" to listOf(user3)), Array<StoryDTO>::class.java)

        Assertions.assertEquals(3, storiesUser1.body?.size)
        Assertions.assertEquals(2, storiesUser2.body?.size)
        Assertions.assertEquals(1, storiesUser3.body?.size)
    }

    @Test
    @Order(8)
    fun getStoriesFiltered_combinedFilter() {
        val tag1 = storyService.createStoryTag(StoryTag(null, "Tommy", "Tommy Test", StoryTagType.CHARACTER))
        val tag2 = storyService.createStoryTag(StoryTag(null, "Short", "Short Reads", StoryTagType.CATEGORY))
        val tag3 = storyService.createStoryTag(StoryTag(null, "Fantasy", "Fantasy", StoryTagType.GENRE))
        val tag4 = storyService.createStoryTag(StoryTag(null, "Socks", "Wet Socks", StoryTagType.WARNING))

        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
            storyDto.storyTags = setOf(tag1, tag2)
        }
        val storyDTO2 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2))
            storyDto.storyTags = setOf(tag2, tag3)
        }
        val storyDTO3 = StoryDTO().also { storyDto ->
            storyDto.author =
                mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2), UserIdNameDTO.from(user3))
            storyDto.chapters = mutableListOf()
            storyDto.storyTags = setOf(tag3, tag4)
        }

        storyService.createStory(storyDTO)
        storyService.createStory(storyDTO2)
        storyService.createStory(storyDTO3)

        val url = generateUrl("/api/story/filtered")
        val storiesUser1AndCat = restTemplate.postForEntity(
            url, mapOf("authors" to listOf(user1), "tags" to listOf(tag2)), Array<StoryDTO>::class.java
        )
        val storiesUser2AndWarn = restTemplate.postForEntity(
            url, mapOf("authors" to listOf(user2), "tags" to listOf(tag4)), Array<StoryDTO>::class.java
        )
        val storiesUser3AndChar = restTemplate.postForEntity(
            url, mapOf("authors" to listOf(user3), "tags" to listOf(tag1)), Array<StoryDTO>::class.java
        )

        Assertions.assertEquals(2, storiesUser1AndCat.body?.size)
        Assertions.assertEquals(1, storiesUser2AndWarn.body?.size)
        Assertions.assertEquals(0, storiesUser3AndChar.body?.size)
    }

    @Test
    @Order(9)
    fun createStory_success() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }
        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val url = generateUrl("/api/story")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.PUT, HttpEntity(storyDTO, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, storyRepository.count())
    }

    @Test
    @Order(10)
    fun createStory_invalid() {
        val storyDTO = StoryDTO()
        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val url = generateUrl("/api/story")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.PUT, HttpEntity(storyDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals(0, storyRepository.count())
    }

    @Test
    @Order(11)
    fun createStory_notLoggedIn() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        val url = generateUrl("/api/story")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.PUT, HttpEntity(storyDTO))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals(0, storyRepository.count())
    }

    @Test
    @Order(12)
    fun editStory() {
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

        val editedStoryDTO = StoryDTO().also {
            it.id = story.id
            it.author = mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2))
            it.title = "Edited Title"
        }

        var url = generateUrl("/api/story/")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.POST, HttpEntity(editedStoryDTO, headers))

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("Edited Title", retrievedStory.body?.title)
        Assertions.assertEquals(2, retrievedStory.body?.author?.size)
    }

    @Test
    @Order(13)
    fun editStory_unknownStory() {
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

        val editedStoryDTO = StoryDTO().also {
            it.id = -1
            it.author = mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2))
            it.title = "Edited Title"
        }

        var url = generateUrl("/api/story/")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.POST, HttpEntity(editedStoryDTO, headers))

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Test Title", retrievedStory.body?.title)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
    }

    @Test
    @Order(14)
    fun editStory_invalid() {
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

        val editedStoryDTO = StoryDTO()

        var url = generateUrl("/api/story/")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.POST, HttpEntity(editedStoryDTO, headers))

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Test Title", retrievedStory.body?.title)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
    }

    @Test
    @Order(15)
    fun editStory_notAuthor() {
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

        val editedStoryDTO = StoryDTO().also {
            it.id = -1
            it.author = mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2))
            it.title = "Edited Title"
        }

        var url = generateUrl("/api/story/")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.POST, HttpEntity(editedStoryDTO, headers))

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Test Title", retrievedStory.body?.title)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
    }

    @Test
    @Order(16)
    fun editStory_Admin() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
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

        val editedStoryDTO = StoryDTO().also {
            it.id = story.id
            it.author = mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user2))
            it.title = "Edited Title"
        }

        var url = generateUrl("/api/story/")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.POST, HttpEntity(editedStoryDTO, headers))

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("Edited Title", retrievedStory.body?.title)
        Assertions.assertEquals(2, retrievedStory.body?.author?.size)
    }

    @Test
    @Order(17)
    fun deleteStory() {
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

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND, retrievedStory.statusCode)
        Assertions.assertNull(retrievedStory.body)
    }

    @Test
    @Order(18)
    fun deleteStory_unknown() {
        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user1", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val url = generateUrl("/api/story/-1")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(19)
    fun deleteStory_notAuthor() {
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

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertNotNull(retrievedStory.body)
    }

    @Test
    @Order(20)
    fun deleteStory_Admin() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.title = "Test Title"
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

        var url = generateUrl("/api/story/${story.id}")
        val response = restTemplate.exchange<Unit>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND, retrievedStory.statusCode)
        Assertions.assertNull(retrievedStory.body)
    }

    @Test
    @Order(21)
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
    @Order(22)
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
    @Order(23)
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
    @Order(24)
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
    @Order(25)
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
    @Order(26)
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
    @Order(27)
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
    @Order(28)
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
    @Order(29)
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
    @Order(30)
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
    @Order(31)
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
    @Order(32)
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
    @Order(33)
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

    @Test
    @Order(34)
    fun addContributorToStory() {
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

        var url = generateUrl("/api/story/${story.id}/contributor")
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
        Assertions.assertEquals(1, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.beta?.size)
    }

    @Test
    @Order(35)
    fun addContributorToStory_invalidStory() {
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

        var url = generateUrl("/api/story/-1/contributor")
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
    }

    @Test
    @Order(36)
    fun addContributorToStory_invalidTag() {
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

        var url = generateUrl("/api/story/${story.id}/contributor")
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
    }

    @Test
    @Order(37)
    fun addContributorToStory_invalidUser() {
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

        var url = generateUrl("/api/story/${story.id}/contributor")
        val storyContributorDTO = """{ "user" : null, "userTag" : "BETA" }"""

        val response = restTemplate.exchange<String>(url, HttpMethod.PUT, HttpEntity(storyContributorDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(0, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(0, retrievedStory.body?.beta?.size)
    }

    @Test
    @Order(38)
    fun addContributorToStory_notAuthor() {
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

        var url = generateUrl("/api/story/${story.id}/contributor")
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
    }

    @Test
    @Order(39)
    fun addContributorToStory_asAdmin() {
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

        var url = generateUrl("/api/story/${story.id}/contributor")
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
        Assertions.assertEquals(1, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.beta?.size)
    }

    @Test
    @Order(40)
    fun removeContributorFromStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
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

        var url = generateUrl("/api/story/${story.id}/contributor")
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
    }

    @Test
    @Order(41)
    fun removeContributorFromStory_invalidStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
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

        var url = generateUrl("/api/story/-1/contributor")
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
        Assertions.assertEquals(1, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.beta?.size)
    }

    @Test
    @Order(42)
    fun removeContributorFromStory_invalidTag() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
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

        var url = generateUrl("/api/story/${story.id}/contributor")
        val storyContributorDTO =
            """{ "user" : { "id": ${user2.id}, "username" : "${user2.username}" }, "userTag" : "RANDO" }"""

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(1, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.beta?.size)
    }

    @Test
    @Order(43)
    fun removeContributorFromStory_invalidUser() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
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

        var url = generateUrl("/api/story/${story.id}/contributor")
        val storyContributorDTO = """{ "user" : null, "userTag" : "BETA" }"""

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(storyContributorDTO, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        val retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.author?.size)
        Assertions.assertEquals(1, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.beta?.size)
    }

    @Test
    @Order(44)
    fun removeContributorFromStory_notAuthor() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
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

        var url = generateUrl("/api/story/${story.id}/contributor")
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
        Assertions.assertEquals(1, retrievedStory.body?.artist?.size)
        Assertions.assertEquals(1, retrievedStory.body?.beta?.size)
    }

    @Test
    @Order(45)
    fun removeContributorFromStory_asAdmin() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
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

        var url = generateUrl("/api/story/${story.id}/contributor")
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
    }

    @Test
    @Order(46)
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
    @Order(47)
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
    @Order(48)
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
    @Order(49)
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
    @Order(50)
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
    @Order(51)
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
    @Order(52)
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
    @Order(53)
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
    @Order(54)
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
    @Order(55)
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
    @Order(56)
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
    @Order(57)
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
    @Order(58)
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
    @Order(59)
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

    @Test
    @Order(60)
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
    @Order(61)
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
    @Order(62)
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
    @Order(63)
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
    @Order(64)
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
    @Order(65)
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
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
    @Order(66)
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
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
    @Order(67)
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
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
    @Order(68)
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
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
    @Order(69)
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
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
    @Order(70)
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
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

    @Test
    @Order(71)
    fun deleteChapterComment() {
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        url =
            generateUrl(
                "/api/story/chapter/${retrievedStory.body?.chapters?.first()?.id}/" +
                        "comment/${chapterCommentDTOs?.first()?.id}"
            )

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(72)
    fun deleteChapterComment_invalidChapter() {
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        url = generateUrl("/api/story/chapter/-1/comment/${chapterCommentDTOs?.first()?.id}")

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(73)
    fun deleteChapterComment_invalidComment() {
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        url = generateUrl("/api/story/chapter/${retrievedStory.body?.chapters?.first()?.id}/comment/-1")

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(74)
    fun deleteChapterComment_invalidAuthor() {
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user3", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        url =
            generateUrl(
                "/api/story/chapter/${retrievedStory.body?.chapters?.first()?.id}/" +
                        "comment/${chapterCommentDTOs?.first()?.id}"
            )

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(75)
    fun deleteChapterComment_invalidUser() {
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        url =
            generateUrl(
                "/api/story/chapter/${retrievedStory.body?.chapters?.first()?.id}/" +
                        "comment/${chapterCommentDTOs?.first()?.id}"
            )

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(1, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(76)
    fun deleteChapterComment_asAdmin() {
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

        val success = storyService.addChapterComment(story.chapters.first().id ?: -1, commentDTO)
        if (!success) fail("Couldn't add comment")

        userRoleService.changeRole(user3, Role.ADMINISTRATOR)

        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl, LoginRequest("user3", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        var url = generateUrl("/api/story/${story.id}")
        var retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        val chapterCommentDTOs = retrievedStory.body?.chapters?.first()?.comments

        url = generateUrl(
            "/api/story/chapter/${retrievedStory.body?.chapters?.first()?.id}/" +
                    "comment/${chapterCommentDTOs?.first()?.id}"
        )

        val response = restTemplate.exchange<String>(url, HttpMethod.DELETE, HttpEntity(null, headers))

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        url = generateUrl("/api/story/${story.id}")
        retrievedStory = restTemplate.getForEntity(url, StoryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, retrievedStory.statusCode)
        Assertions.assertEquals(0, retrievedStory.body?.chapters?.first()?.comments?.size)
    }

    @Test
    @Order(77)
    fun getUserContributions() {
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

        var url = generateUrl("/api/story/${user1.id}/contributions")
        val user1Contributions = restTemplate.getForEntity(url, UserContributionDTO::class.java)
        url = generateUrl("/api/story/${user2.id}/contributions")
        val user2Contributions = restTemplate.getForEntity(url, UserContributionDTO::class.java)
        url = generateUrl("/api/story/${user3.id}/contributions")
        val user3Contributions = restTemplate.getForEntity(url, UserContributionDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, user1Contributions.statusCode)
        Assertions.assertEquals(HttpStatus.OK, user2Contributions.statusCode)
        Assertions.assertEquals(HttpStatus.OK, user3Contributions.statusCode)
        Assertions.assertEquals(1, user1Contributions.body?.stories?.size)
        Assertions.assertEquals(0, user1Contributions.body?.chapters?.size)
        Assertions.assertEquals(0, user2Contributions.body?.stories?.size)
        Assertions.assertEquals(1, user2Contributions.body?.chapters?.size)
        Assertions.assertEquals(0, user3Contributions.body?.stories?.size)
        Assertions.assertEquals(1, user3Contributions.body?.chapters?.size)
    }

    @Test
    @Order(78)
    fun getUserContributions_invalidUser() {
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

        val url = generateUrl("/api/story/-1/contributions")
        val user1Contributions = restTemplate.getForEntity(url, UserContributionDTO::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, user1Contributions.statusCode)
    }
}
