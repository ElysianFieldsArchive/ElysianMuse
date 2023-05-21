package org.darkSolace.muse.story

import jakarta.transaction.Transactional
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.story.model.Rating
import org.darkSolace.muse.story.model.Story
import org.darkSolace.muse.story.model.StoryTag
import org.darkSolace.muse.story.model.StoryTagType
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.service.StoryChapterService
import org.darkSolace.muse.story.service.StoryService
import org.darkSolace.muse.story.service.StoryTagService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class StoryServiceTests(
    @Autowired private val storyService: StoryService,
    @Autowired private val storyChapterService: StoryChapterService,
    @Autowired private val storyTagService: StoryTagService,
    @Autowired private val storyRepository: StoryRepository,
    @Autowired private val userService: UserService,
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
    fun getStoryById() {
        var story = Story()
        story.author = mutableSetOf(user1)
        story = storyRepository.save(story)

        val dbStory = storyService.getStoryById(story.id ?: -1)

        Assertions.assertEquals(story.id, dbStory?.id)
    }

    @Test
    @Order(2)
    fun getChapterById() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var dbStory = storyRepository.findAll().first()

        storyChapterService.addChapter(ChapterDTO().also {
            it.storyId = dbStory.id
            it.content = "Content"
        })

        dbStory = storyRepository.findAll().first()

        val dbChapter = storyChapterService.getChapterById(dbStory.chapters.first().id ?: -1)

        Assertions.assertEquals(dbStory.chapters.first().id, dbChapter?.id)
    }

    @Test
    @Order(3)
    fun getAllStories() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }
        val storyDTO2 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }
        val storyDTO3 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)
        storyService.createStory(storyDTO2)
        storyService.createStory(storyDTO3)

        val stories = storyService.getAllStories()

        Assertions.assertEquals(3, stories.size)
    }

    @Test
    @Order(4)
    fun getStoriesFiltered_noFilter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }
        val storyDTO2 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }
        val storyDTO3 = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)
        storyService.createStory(storyDTO2)
        storyService.createStory(storyDTO3)

        val stories = storyService.getStoriesFiltered()

        Assertions.assertEquals(3, stories.size)
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

        val storiesAll = storyService.getStoriesFiltered(ratings = listOf(Rating.PARENTAL_GUIDANCE, Rating.NC_17))
        val storiesPG = storyService.getStoriesFiltered(ratings = listOf(Rating.PARENTAL_GUIDANCE))
        val storiesNC = storyService.getStoriesFiltered(ratings = listOf(Rating.NC_17))
        val storiesAdult = storyService.getStoriesFiltered(ratings = listOf(Rating.ADULT_ONLY))

        Assertions.assertEquals(3, storiesAll.size)
        Assertions.assertEquals(2, storiesPG.size)
        Assertions.assertEquals(1, storiesNC.size)
        Assertions.assertEquals(0, storiesAdult.size)
    }

    @Test
    @Order(6)
    fun getStoriesFiltered_tagFilter() {
        val tag1 = storyTagService.createStoryTag(StoryTag(null, "Tommy", "Tommy Test", StoryTagType.CHARACTER))
        val tag2 = storyTagService.createStoryTag(StoryTag(null, "Short", "Short Reads", StoryTagType.CATEGORY))
        val tag3 = storyTagService.createStoryTag(StoryTag(null, "Fantasy", "Fantasy", StoryTagType.GENRE))
        val tag4 = storyTagService.createStoryTag(StoryTag(null, "Socks", "Wet Socks", StoryTagType.WARNING))

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

        val storyChar = storyService.getStoriesFiltered(tags = listOf(tag1))
        Assertions.assertEquals(1, storyChar.size)
        Assertions.assertTrue(storyChar.all { it.storyTags.any { it.type == StoryTagType.CHARACTER } })

        val storyCat = storyService.getStoriesFiltered(tags = listOf(tag2))
        Assertions.assertEquals(2, storyCat.size)
        Assertions.assertTrue(storyCat.all { it.storyTags.any { it.type == StoryTagType.CATEGORY } })

        val storyGenre = storyService.getStoriesFiltered(tags = listOf(tag3))
        Assertions.assertEquals(2, storyGenre.size)
        Assertions.assertTrue(storyGenre.all { it.storyTags.any { it.type == StoryTagType.GENRE } })

        val storyWarn = storyService.getStoriesFiltered(tags = listOf(tag4))
        Assertions.assertEquals(1, storyWarn.size)
        Assertions.assertTrue(storyWarn.all { it.storyTags.any { it.type == StoryTagType.WARNING } })
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

        val storiesUser1 = storyService.getStoriesFiltered(authors = listOf(user1))
        val storiesUser2 = storyService.getStoriesFiltered(authors = listOf(user2))
        val storiesUser3 = storyService.getStoriesFiltered(authors = listOf(user3))

        Assertions.assertEquals(3, storiesUser1.size)
        Assertions.assertEquals(2, storiesUser2.size)
        Assertions.assertEquals(1, storiesUser3.size)

    }

    @Test
    @Order(8)
    fun getStoriesFiltered_combinedFilter() {
        val tag1 = storyTagService.createStoryTag(StoryTag(null, "Tommy", "Tommy Test", StoryTagType.CHARACTER))
        val tag2 = storyTagService.createStoryTag(StoryTag(null, "Short", "Short Reads", StoryTagType.CATEGORY))
        val tag3 = storyTagService.createStoryTag(StoryTag(null, "Fantasy", "Fantasy", StoryTagType.GENRE))
        val tag4 = storyTagService.createStoryTag(StoryTag(null, "Socks", "Wet Socks", StoryTagType.WARNING))

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

        val storiesUser1AndCat = storyService.getStoriesFiltered(authors = listOf(user1), tags = listOf(tag2))
        val storiesUser2AndWarn = storyService.getStoriesFiltered(authors = listOf(user2), tags = listOf(tag4))
        val storiesUser3AndChar = storyService.getStoriesFiltered(authors = listOf(user3), tags = listOf(tag1))

        Assertions.assertEquals(2, storiesUser1AndCat.size)
        Assertions.assertEquals(1, storiesUser2AndWarn.size)
        Assertions.assertEquals(0, storiesUser3AndChar.size)
    }

    @Test
    @Order(9)
    fun createStory_success() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        val success = storyService.createStory(storyDTO)

        Assertions.assertEquals(1, storyRepository.count())
        Assertions.assertTrue(success)
    }

    @Test
    @Order(10)
    fun createStory_fail() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.chapters = mutableListOf()
        }

        val success = storyService.createStory(storyDTO)

        Assertions.assertEquals(0, storyRepository.count())
        Assertions.assertFalse(success)
    }

    @Test
    @Order(11)
    @Transactional
    fun editStory_success() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        val story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val editedStoryDTO = StoryDTO.from(story).also {
            it.author = UserIdNameDTO.fromCollection(story.author).plus(UserIdNameDTO.from(user2)).toSet()
        }

        val success = storyService.editStory(editedStoryDTO)

        val editedStory = storyService.getAllStories().firstOrNull() ?: fail("Edited story not found")

        Assertions.assertTrue(success)
        Assertions.assertEquals(2, editedStory.author.size)
    }

    @Test
    @Order(12)
    @Transactional
    fun editStory_wrongStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        val story = storyService.getAllStories().firstOrNull() ?: fail("Story not found")

        val editedStoryDTO = StoryDTO.from(story).also {
            it.id = -1
            it.author = UserIdNameDTO.fromCollection(story.author).plus(UserIdNameDTO.from(user2)).toSet()
        }

        val success = storyService.editStory(editedStoryDTO)

        val editedStory = storyService.getAllStories().firstOrNull() ?: fail("Edited story not found")

        Assertions.assertFalse(success)
        Assertions.assertEquals(1, editedStory.author.size)
    }

    @Test
    @Order(13)
    fun deleteStory() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story: Story? = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        storyService.deleteStory(story?.id ?: -1)

        story = storyRepository.findAll().firstOrNull()

        Assertions.assertNull(story)
    }

    @Test
    @Order(14)
    fun addChapter() {
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
        val chapter = story.chapters.first()

        Assertions.assertEquals(1, story.chapters.size)
        Assertions.assertEquals("Test Content", chapter.content)
        Assertions.assertEquals("Test Title", chapter.title)
    }

    @Test
    @Order(15)
    @Transactional
    fun editChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
        }

        storyChapterService.addChapter(chapterDTO)

        var editedChapter =
            storyService.getAllStories().firstOrNull()?.chapters?.first() ?: fail("Story with chapter not found")

        editedChapter = editedChapter.also {
            it.content = "Edited Content"
            it.title = "Edited Title"
        }

        storyChapterService.editChapter(ChapterDTO.from(editedChapter))

        story = storyRepository.findAll().firstOrNull() ?: fail("Story with chapter not found")
        val chapter = story.chapters.first()

        Assertions.assertEquals(1, story.chapters.size)
        Assertions.assertEquals("Edited Content", chapter.content)
        Assertions.assertEquals("Edited Title", chapter.title)
    }

    @Test
    @Order(16)
    @Transactional
    fun deleteChapter() {
        val storyDTO = StoryDTO().also { storyDto ->
            storyDto.author = mutableSetOf(UserIdNameDTO.from(user1))
            storyDto.chapters = mutableListOf()
        }

        storyService.createStory(storyDTO)

        var story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        val chapterDTO = ChapterDTO().also {
            it.storyId = story.id
            it.content = "Test Content"
            it.title = "Test Title"
        }

        storyChapterService.addChapter(chapterDTO)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        Assertions.assertEquals(1, story.chapters.size)

        val chapter = story.chapters.first()

        storyChapterService.deleteChapter(chapter.id ?: -1)

        story = storyRepository.findAll().firstOrNull() ?: fail("Story not found")

        Assertions.assertEquals(0, story.chapters.size)
    }
}
