package org.darkSolace.muse.news

import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.news.model.dto.NewsCommentDTO
import org.darkSolace.muse.news.model.dto.NewsEntryDTO
import org.darkSolace.muse.news.repository.NewsRepository
import org.darkSolace.muse.news.service.NewsService
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired

internal class NewsServiceTests : TestBase() {
    @Autowired
    lateinit var newsService: NewsService

    @Autowired
    lateinit var newsRepository: NewsRepository

    @Autowired
    lateinit var userService: UserService

    @Test
    @Order(1)
    fun createNews() {
        val sizeBefore = newsRepository.count()
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        val success = newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 1"
            it.subject = "Test Subject 1"
            it.author = author
        }))
        val sizeAfter = newsRepository.count()
        val news = newsRepository.findAll().firstOrNull()

        Assertions.assertTrue(success)
        Assertions.assertEquals(sizeBefore + 1, sizeAfter)
        Assertions.assertNotNull(news)
        Assertions.assertEquals("Test Subject 1", news?.subject)
        Assertions.assertEquals("Test Content 1", news?.content)

    }

    @Test
    fun getLast() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        //create 5 news articles
        repeat(5) { i ->
            newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
                it.content = "Test Content $i"
                it.subject = "Test Subject $i"
                it.author = author
            }))
        }

        //retrieve last 3 entries
        val entries = newsService.getLast(3)

        Assertions.assertEquals(3, entries.size, "Size correct?")
        Assertions.assertTrue(
            entries.map { it.subject }.containsAll(
                listOf("Test Subject 4", "Test Subject 3", "Test Subject 2")
            ), "Content correct?"
        )
        Assertions.assertEquals("Test Subject 4", entries.first().subject, "Ordering correct?")
    }

    @Test
    fun addCommentToNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 1"
            it.subject = "Test Subject 1"
            it.author = author
        }))

        var news = newsRepository.findAll().firstOrNull()

        val success = newsService.addCommentToNews(news?.id ?: -1, NewsCommentDTO.from(NewsComment().also {
            it.author = author
            it.content = "Test Comment"
        }))

        news = newsRepository.findAll().firstOrNull()

        Assertions.assertTrue(success)
        Assertions.assertNotNull(news)
        Assertions.assertEquals(1, news?.newsComments?.size)
        Assertions.assertEquals("Test Comment", news?.newsComments?.first()?.content)
    }

    @Test
    fun addCommentToNews_invalidNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))

        val success = newsService.addCommentToNews(500, NewsCommentDTO.from(NewsComment().also {
            it.author = author
            it.content = "Test Comment"
        }))

        Assertions.assertFalse(success)
    }

    @Test
    fun addCommentToNews_noAuthor() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 1"
            it.subject = "Test Subject 1"
            it.author = author
        }))

        var news = newsRepository.findAll().firstOrNull()

        val success = newsService.addCommentToNews(news?.id ?: -1, NewsCommentDTO.from(NewsComment().also {
            it.content = "Test Comment"
        }))

        news = newsRepository.findAll().firstOrNull()

        Assertions.assertFalse(success)
        Assertions.assertNotNull(news)
    }

    @Test
    fun getNews() {
        val count = newsRepository.count()
        Assertions.assertEquals(0, count)

        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        //create 5 news articles
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content"
            it.subject = "Test Subject"
            it.author = author
        }))
        val newsDirect = newsRepository.findAll().firstOrNull() ?: fail("No news found")

        var news = newsService.getNews(newsDirect.id ?: -1)

        Assertions.assertNotNull(news)

        news = newsService.getNews(-1)
        Assertions.assertNull(news)
    }

    @Test
    fun editNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.subject = "Original Subject"
            it.content = "Original Content"
            it.author = author
        }))

        var news = newsRepository.findAll().firstOrNull() ?: fail("Original news was not created.")

        val editedNews = NewsEntryDTO.from(NewsEntry().also {
            it.subject = "Edited Subject"
            it.content = "Edited Content"
            it.author = author
        })

        newsService.editNews(news.id ?: -1, editedNews)

        news = newsRepository.findAll().firstOrNull() ?: fail("Edited news was not created.")

        Assertions.assertEquals("Edited Subject", news.subject)
        Assertions.assertEquals("Edited Content", news.content)
    }

    @Test
    fun editComment() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 1"
            it.subject = "Test Subject 1"
            it.author = author
        }))

        var news = newsRepository.findAll().firstOrNull()

        newsService.addCommentToNews(news?.id ?: -1, NewsCommentDTO.from(NewsComment().also {
            it.author = author
            it.content = "Test Comment"
        }))

        news = newsRepository.findAll().firstOrNull()
        val id = news?.newsComments?.first()?.id ?: -1
        val editedComment = NewsCommentDTO.from(NewsComment().also {
            it.author = author
            it.content = "Edited Comment"
        })

        newsService.editComment(id, editedComment)

        news = newsRepository.findAll().firstOrNull()

        Assertions.assertEquals("Edited Comment", news?.newsComments?.first()?.content)
    }

    @Test
    fun getAllNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "1234", "test@example.org"))
        //create 8 news articles
        repeat(8) { i ->
            newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
                it.content = "Test Content $i"
                it.subject = "Test Subject $i"
                it.author = author
            }))
        }

        val entries = newsService.getAllNews()

        Assertions.assertEquals(8, entries.size, "Size correct?")
        Assertions.assertEquals("Test Subject 7", entries.first().subject, "Ordering correct?")
    }
}