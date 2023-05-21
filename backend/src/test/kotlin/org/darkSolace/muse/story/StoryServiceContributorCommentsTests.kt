package org.darkSolace.muse.story

import jakarta.transaction.Transactional
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.story.model.dto.ChapterCommentDTO
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.service.StoryChapterService
import org.darkSolace.muse.story.service.StoryContributionService
import org.darkSolace.muse.story.service.StoryService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class StoryServiceContributorCommentsTests(
    @Autowired private val storyService: StoryService,
    @Autowired private val storyRepository: StoryRepository,
    @Autowired private val userService: UserService,
    @Autowired private val storyChapterService: StoryChapterService,
    @Autowired private val storyContributionService: StoryContributionService,
) : TestBase() {
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User

    @BeforeEach
    fun setUp() {
        user1 = userService.createUserFromSignUpRequest(SignUpRequest("user1", "123456", "user1@example.com"))
            ?: fail("Coundn't create User")
        user2 = userService.createUserFromSignUpRequest(SignUpRequest("user2", "123456", "user2@example.com"))
            ?: fail("Coundn't create User")
        user3 = userService.createUserFromSignUpRequest(SignUpRequest("user3", "123456", "user3@example.com"))
            ?: fail("Coundn't create User")
    }

    @Test
    @Order(1)
    @Transactional
    fun addContributorToStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyService.getAllStories().firstOrNull() ?: fail("Story not found")

        Assertions.assertEquals(1, story.author.size)
        Assertions.assertEquals(0, story.artist.size)
        Assertions.assertEquals(0, story.beta.size)

        storyContributionService.addContributorToStory(story.id ?: -1, user2.id ?: -1, UserTag.BETA)
        storyContributionService.addContributorToStory(story.id ?: -1, user2.id ?: -1, UserTag.ARTIST)
        storyContributionService.addContributorToStory(story.id ?: -1, user2.id ?: -1, UserTag.AUTHOR)

        story = storyService.getAllStories().firstOrNull() ?: fail("Story not found")

        Assertions.assertEquals(2, story.author.size)
        Assertions.assertEquals(1, story.artist.size)
        Assertions.assertEquals(1, story.beta.size)
    }

    @Test
    @Order(2)
    @Transactional
    fun removeContributorFromStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1), UserIdNameDTO.from(user3))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyService.getAllStories().firstOrNull() ?: fail("Story not found")

        Assertions.assertEquals(2, story.author.size)
        Assertions.assertEquals(1, story.artist.size)
        Assertions.assertEquals(1, story.beta.size)

        storyContributionService.removeContributorFromStory(story.id ?: -1, user3.id ?: -1, UserTag.AUTHOR)
        storyContributionService.removeContributorFromStory(story.id ?: -1, user2.id ?: -1, UserTag.ARTIST)
        storyContributionService.removeContributorFromStory(story.id ?: -1, user3.id ?: -1, UserTag.BETA)

        story = storyService.getAllStories().firstOrNull() ?: fail("Story not found")

        Assertions.assertEquals(1, story.author.size)
        Assertions.assertEquals(0, story.artist.size)
        Assertions.assertEquals(0, story.beta.size)
    }

    @Test
    @Order(3)
    @Transactional
    fun addContributorToChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO.from(Chapter().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
        })

        storyChapterService.addChapter(chapterDTO)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        var chapter = story.chapters.first()

        Assertions.assertEquals(0, chapter.artist.size)
        Assertions.assertEquals(0, chapter.beta.size)

        storyChapterService.addContributorToChapter(story.id ?: -1, chapter.id ?: -1, user2.id ?: -1, UserTag.ARTIST)
        storyChapterService.addContributorToChapter(story.id ?: -1, chapter.id ?: -1, user3.id ?: -1, UserTag.BETA)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        chapter = story.chapters.first()

        Assertions.assertEquals(1, chapter.artist.size)
        Assertions.assertEquals(1, chapter.beta.size)
    }

    @Test
    @Order(4)
    @Transactional
    fun removeContributorFromChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO.from(Chapter().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
            it.artist = mutableSetOf(user2)
            it.beta = mutableSetOf(user3)
        })

        storyChapterService.addChapter(chapterDTO)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        val chapter = story.chapters.first()

        Assertions.assertEquals(1, chapter.artist.size)
        Assertions.assertEquals(1, chapter.beta.size)

        storyChapterService.removeContributorFromChapter(
            story.id ?: -1,
            chapter.id ?: -1,
            user2.id ?: -1,
            UserTag.ARTIST
        )
        storyChapterService.removeContributorFromChapter(story.id ?: -1, chapter.id ?: -1, user3.id ?: -1, UserTag.BETA)
    }

    @Test
    @Order(5)
    @Transactional
    fun addChapterComment() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO.from(Chapter().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
        })

        storyChapterService.addChapter(chapterDTO)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        var chapter = story.chapters.first()

        Assertions.assertEquals(0, chapter.comments.size)

        storyChapterService.addChapterComment(chapter.id ?: -1, ChapterCommentDTO().also {
            it.authorApproved = true
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = chapter.id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        })

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        chapter = story.chapters.first()

        Assertions.assertEquals(1, chapter.comments.size)
        Assertions.assertEquals("Test Comment", chapter.comments.first().content)

        storyChapterService.addChapterComment(chapter.id ?: -1, ChapterCommentDTO().also {
            it.authorApproved = true
            it.author = UserIdNameDTO.from(user3)
            it.chapterId = chapter.id
            it.content = "Test Reply"
            it.publishedDate = Date()
            it.referenceComment = chapter.comments.first().id
        })

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        chapter = story.chapters.first()

        Assertions.assertEquals(2, chapter.comments.size)
        Assertions.assertTrue(chapter.comments.any { it.content == "Test Reply" })
    }

    @Test
    @Order(6)
    @Transactional
    fun editChapterComment() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO.from(Chapter().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
        })

        storyChapterService.addChapter(chapterDTO)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        var chapter = story.chapters.first()

        storyChapterService.addChapterComment(chapter.id ?: -1, ChapterCommentDTO().also {
            it.authorApproved = true
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = chapter.id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        })

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        chapter = story.chapters.first()

        Assertions.assertEquals(1, chapter.comments.size)
        Assertions.assertEquals("Test Comment", chapter.comments.first().content)

        val editedChapterCommentDTO = ChapterCommentDTO().also {
            it.id = chapter.comments.first().id
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = chapter.id
            it.content = "Edited Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        }

        storyChapterService.editChapterComment(editedChapterCommentDTO)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        chapter = story.chapters.first()

        Assertions.assertEquals(1, chapter.comments.size)
        Assertions.assertEquals("Edited Comment", chapter.comments.first().content)
    }

    @Test
    @Order(7)
    @Transactional
    fun deleteChapterComment() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO.from(Chapter().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
        })

        storyChapterService.addChapter(chapterDTO)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        var chapter = story.chapters.first()

        storyChapterService.addChapterComment(chapter.id ?: -1, ChapterCommentDTO().also {
            it.authorApproved = true
            it.author = UserIdNameDTO.from(user2)
            it.chapterId = chapter.id
            it.content = "Test Comment"
            it.publishedDate = Date()
            it.referenceComment = null
        })

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        chapter = story.chapters.first()

        Assertions.assertEquals(1, chapter.comments.size)

        storyChapterService.deleteChapterComment(chapter.comments.first().id ?: -1)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        chapter = story.chapters.first()

        Assertions.assertEquals(0, chapter.comments.size)
    }

    @Test
    @Order(8)
    @Transactional
    fun getUserContributions() {
        var user1Contributions = storyContributionService.getUserContributions(user1)
        var user2Contributions = storyContributionService.getUserContributions(user2)
        var user3Contributions = storyContributionService.getUserContributions(user3)

        Assertions.assertEquals(0, user1Contributions.stories.size)
        Assertions.assertEquals(0, user1Contributions.chapters.size)
        Assertions.assertEquals(0, user2Contributions.stories.size)
        Assertions.assertEquals(0, user2Contributions.chapters.size)
        Assertions.assertEquals(0, user3Contributions.stories.size)
        Assertions.assertEquals(0, user3Contributions.chapters.size)

        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.artist = mutableSetOf(UserIdNameDTO.from(user2))
            storyDto.beta = mutableSetOf(UserIdNameDTO.from(user3))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO.from(Chapter().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
            it.artist = mutableSetOf(user2)
            it.beta = mutableSetOf(user3)
        })

        storyChapterService.addChapter(chapterDTO)

        user1Contributions = storyContributionService.getUserContributions(user1)
        user2Contributions = storyContributionService.getUserContributions(user2)
        user3Contributions = storyContributionService.getUserContributions(user3)

        Assertions.assertEquals(1, user1Contributions.stories.size)
        Assertions.assertEquals(0, user1Contributions.chapters.size)
        Assertions.assertEquals(1, user2Contributions.stories.size)
        Assertions.assertEquals(1, user2Contributions.chapters.size)
        Assertions.assertEquals(1, user3Contributions.stories.size)
        Assertions.assertEquals(1, user3Contributions.chapters.size)
    }
}
