package org.darkSolace.muse.news

import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.news.model.dto.NewsCommentDTO
import org.darkSolace.muse.news.model.dto.NewsEntryDTO
import org.darkSolace.muse.news.repository.NewsRepository
import org.darkSolace.muse.news.service.NewsService
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
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
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
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
    @Order(2)
    fun getLast() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
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
    @Order(3)
    fun addCommentToNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
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
    @Order(4)
    fun addCommentToNews_invalidNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")

        val success = newsService.addCommentToNews(500, NewsCommentDTO.from(NewsComment().also {
            it.author = author
            it.content = "Test Comment"
        }))

        Assertions.assertFalse(success)
    }

    @Test
    @Order(5)
    fun addCommentToNews_noAuthor() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 1"
            it.subject = "Test Subject 1"
            it.author = author
        }))

        var news = newsRepository.findAll().firstOrNull()

        val success = newsService.addCommentToNews(news?.id ?: -1, NewsCommentDTO().also {
            it.content = "Test Content"
        })

        news = newsRepository.findAll().firstOrNull()

        Assertions.assertFalse(success)
        Assertions.assertNotNull(news)
    }

    @Test
    @Order(6)
    fun getNews() {
        val count = newsRepository.count()
        Assertions.assertEquals(0, count)

        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
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
    @Order(7)
    fun editNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
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
    @Order(8)
    fun editComment() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
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
    @Order(9)
    fun getAllNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
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

    @Test
    @Order(10)
    fun deleteNews_success() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        //create 8 news articles
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 0"
            it.subject = "Test Subject 0"
            it.author = author
        }))

        var news = newsRepository.findAll()
        val newsId = news.firstOrNull()?.id ?: fail("News not found")

        Assertions.assertNotNull(newsId)
        Assertions.assertNotEquals(0, news.count())

        newsService.deleteNews(newsId)

        news = newsRepository.findAll()

        Assertions.assertEquals(0, news.count())
    }

    @Test
    @Order(11)
    fun deleteNews_unknownId() {
        var news = newsRepository.findAll()

        Assertions.assertEquals(0, news.count())

        newsService.deleteNews(-1)

        news = newsRepository.findAll()

        Assertions.assertEquals(0, news.count())
    }

    @Test
    @Order(12)
    fun deleteComment_success() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        //create 8 news articles
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 0"
            it.subject = "Test Subject 0"
            it.author = author
        }))

        var news = newsRepository.findAll()
        val newsId = news.firstOrNull()?.id ?: fail("News not found")

        newsService.addCommentToNews(newsId, NewsCommentDTO().apply {
            this.author = UserIdNameDTO.from(author)
            this.content = "TestComment"
        })

        //reload news
        news = newsRepository.findAll()
        var comments = news.firstOrNull()?.newsComments ?: fail("News not found")
        val commentId = comments.firstOrNull()?.id ?: fail("Comment not found")

        Assertions.assertNotNull(newsId)
        Assertions.assertNotEquals(0, news.count())
        Assertions.assertNotEquals(0, comments.size)

        newsService.deleteComment(commentId)

        //reload news
        news = newsRepository.findAll()
        comments = news.firstOrNull()?.newsComments ?: fail("News not found")

        Assertions.assertNotEquals(0, news.count())
        Assertions.assertEquals(0, comments.size)
    }

    @Test
    @Order(13)
    fun deleteComment_unknownId() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        //create 8 news articles
        newsService.createNews(NewsEntryDTO.from(NewsEntry().also {
            it.content = "Test Content 0"
            it.subject = "Test Subject 0"
            it.author = author
        }))

        var news = newsRepository.findAll()
        val newsId = news.firstOrNull()?.id ?: fail("News not found")


        Assertions.assertNotNull(newsId)
        Assertions.assertNotEquals(0, news.count())
        Assertions.assertEquals(0, news.firstOrNull()?.newsComments?.size)

        newsService.deleteComment(-1)

        //reload news
        news = newsRepository.findAll()
        val comments = news.firstOrNull()?.newsComments ?: fail("News not found")

        Assertions.assertNotEquals(0, news.count())
        Assertions.assertEquals(0, comments.size)
    }
}