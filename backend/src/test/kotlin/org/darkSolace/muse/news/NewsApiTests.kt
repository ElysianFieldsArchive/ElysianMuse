package org.darkSolace.muse.news

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.news.model.dto.NewsCommentDTO
import org.darkSolace.muse.news.model.dto.NewsEntryDTO
import org.darkSolace.muse.news.service.NewsService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

class NewsApiTests : TestBase() {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var newsService: NewsService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mailService: MailService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Test
    fun getNewest_defaultSize() {
        //create 5 news articles
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        repeat(5) {
            newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject $it"
                content = "News Content $it"
                this.author = author
            }))
        }
        val url = generateUrl("/api/news/last")
        val response = restTemplate.getForEntity(url, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(3, response.body?.size)
    }

    @Test
    fun getNewest_customSize() {
        //create 5 news articles
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        repeat(5) {
            newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject $it"
                content = "News Content $it"
                this.author = author
            }))
        }
        val url = generateUrl("/api/news/last/5")
        val response = restTemplate.getForEntity(url, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(5, response.body?.size)
    }

    @Test
    fun getNewest_minConstraint() {
        //create 5 news articles
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        repeat(5) {
            newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject $it"
                content = "News Content $it"
                this.author = author
            }))
        }
        val url = generateUrl("/api/news/last/0")
        val response = restTemplate.getForEntity(url, String::class.java)

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }

    @Test
    fun getNews() {
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        val news = newsService.getLast(1).first()

        val url = generateUrl("/api/news/${news.id}")
        val response = restTemplate.getForEntity(url, NewsEntryDTO::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("News Subject", response.body?.subject)
        Assertions.assertEquals("News Content", response.body?.content)
    }

    @Test
    fun getNews_invalidId() {
        val url = generateUrl("/api/news/-1")
        val response = restTemplate.getForEntity(url, NewsEntryDTO::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun getAllNews() {
        val url = generateUrl("/api/news")
        var response = restTemplate.getForEntity(url, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)

        //create 5 news articles
        val author = userService.createUserFromSignUpRequest(SignUpRequest("testUser", "123456", "test@example.org"))
            ?: fail("Couldn't create User")
        repeat(5) {
            newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject $it"
                content = "News Content $it"
                this.author = author
            }))
        }

        response = restTemplate.getForEntity(url, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(5, response.body?.size)
    }

    @Test
    fun postNews_AsAdmin() {
        val newsUrl = generateUrl("/api/news")
        var response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)

        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        //try accessing restricted to role MEMBER method
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject"
                content = "News Content"
                this.author = author
            }), headers)
        )

        Assertions.assertEquals(HttpStatus.OK, putResponse.statusCode)

        response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, response.body?.size)
        Assertions.assertEquals("News Subject", response.body?.first()?.subject)
        Assertions.assertEquals("News Content", response.body?.first()?.content)
    }

    @Test
    fun postNews_AsMod() {
        val newsUrl = generateUrl("/api/news")
        var response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)

        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MODERATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        //try accessing restricted to role MEMBER method
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject"
                content = "News Content"
                this.author = author
            }), headers)
        )

        Assertions.assertEquals(HttpStatus.OK, putResponse.statusCode)

        response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, response.body?.size)
        Assertions.assertEquals("News Subject", response.body?.first()?.subject)
        Assertions.assertEquals("News Content", response.body?.first()?.content)
    }

    @Test
    fun postNews_AsMember() {
        val newsUrl = generateUrl("/api/news")
        var response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)

        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        //try accessing restricted to role MEMBER method
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject"
                content = "News Content"
                this.author = author
            }), headers)
        )

        Assertions.assertEquals(HttpStatus.FORBIDDEN, putResponse.statusCode)

        response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)
    }

    @Test
    fun postNews_SubmitterNotAuthor() {
        val newsUrl = generateUrl("/api/news")
        var response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)

        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        //try accessing restricted to role MEMBER method
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "News Subject"
                content = "News Content"
                this.author = author
            }), headers)
        )

        Assertions.assertEquals(HttpStatus.FORBIDDEN, putResponse.statusCode)

        response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)
    }

    @Test
    fun postNews_invalidNews() {
        val newsUrl = generateUrl("/api/news")
        var response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)

        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        //try accessing restricted to role MEMBER method
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //try to add empty news
        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity(NewsEntryDTO(), headers)
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, putResponse.statusCode)

        response = restTemplate.getForEntity(newsUrl, Array<NewsEntryDTO>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size)
    }

    @Test
    fun editNews_asAdmin() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //edit news
        val newsUrl = generateUrl("/api/news/${news?.id}")

        val response = restTemplate.postForEntity(
            newsUrl, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "Edited Subject"
                content = "Edited Content"
                this.author = author
            }), headers), Unit::class.java
        )

        news = newsService.getNews(news?.id ?: -1)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("Edited Subject", news?.subject)
        Assertions.assertEquals("Edited Content", news?.content)
    }

    @Test
    fun editNews_asMod() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MODERATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //edit news
        val newsUrl = generateUrl("/api/news/${news?.id}")

        val response = restTemplate.postForEntity(
            newsUrl, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "Edited Subject"
                content = "Edited Content"
                this.author = author
            }), headers), Unit::class.java
        )

        news = newsService.getNews(news?.id ?: -1)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("Edited Subject", news?.subject)
        Assertions.assertEquals("Edited Content", news?.content)
    }

    @Test
    fun editNews_asMember() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //edit news
        val newsUrl = generateUrl("/api/news/${news?.id}")

        val response = restTemplate.postForEntity(
            newsUrl, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "Edited Subject"
                content = "Edited Content"
                this.author = author
            }), headers), Unit::class.java
        )

        news = newsService.getNews(news?.id ?: -1)
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        Assertions.assertEquals("News Subject", news?.subject)
        Assertions.assertEquals("News Content", news?.content)
    }

    @Test
    fun editNews_invalidEdit() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //edit news
        val newsUrl = generateUrl("/api/news/${news?.id}")

        val response = restTemplate.postForEntity(
            newsUrl, HttpEntity(NewsEntryDTO(), headers), Unit::class.java
        )

        news = newsService.getNews(news?.id ?: -1)
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals("News Subject", news?.subject)
        Assertions.assertEquals("News Content", news?.content)
    }

    @Test
    fun editNews_invalidNews() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //edit news
        val newsUrl = generateUrl("/api/news/-1")

        val response = restTemplate.postForEntity(
            newsUrl, HttpEntity<NewsEntryDTO>(NewsEntryDTO.from(NewsEntry().apply {
                subject = "Edited Subject"
                content = "Edited Content"
                this.author = author
            }), headers), Unit::class.java
        )

        news = newsService.getNews(news?.id ?: -1)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("News Subject", news?.subject)
        Assertions.assertEquals("News Content", news?.content)
    }

    @Test
    fun addComment_asMember() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()

        //creat commenter
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test2", "123456", "test2@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val commenter = userService.getAll().last()

        //comment on news
        val newsUrl = generateUrl("/api/news/${news.id}/comment")

        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                this.author = UserIdNameDTO.from(commenter)
                content = "Test comment"
            }, headers)
        )

        Assertions.assertEquals(HttpStatus.OK, putResponse.statusCode)
        news = newsService.getAllNews().first()
        Assertions.assertEquals(1, news.newsComments.size)
        Assertions.assertEquals("Test comment", news.newsComments.first().content)
    }

    @Test
    fun addComment_noUser() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()

        //comment on news
        val newsUrl = generateUrl("/api/news/${news.id}/comment")

        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                this.author = null
                content = "Test comment"
            })
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, putResponse.statusCode)
        news = newsService.getAllNews().first()
        Assertions.assertEquals(0, news.newsComments.size)
    }

    @Test
    fun addComment_invalidNews() {
        //creat commenter
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test2", "123456", "test2@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test2", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val commenter = userService.getAll().first()

        //comment on news
        val newsUrl = generateUrl("/api/news/-1/comment")

        val putResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.PUT, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                this.author = UserIdNameDTO.from(commenter)
                content = "Test comment"
            }, headers)
        )

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, putResponse.statusCode)
    }

    @Test
    fun editComment_asMember() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        var comment = news.newsComments.first()

        //login as Member
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "test123"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //edit own comment
        val newsUrl = generateUrl("/api/news/comment/${comment.id}")

        val postResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.POST, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                this.author = UserIdNameDTO.from(author)
                content = "Edited Test comment"
            }, headers)
        )

        comment = newsService.getAllNews().first().newsComments.first()

        Assertions.assertEquals(HttpStatus.OK, postResponse.statusCode)
        Assertions.assertEquals("Edited Test comment", comment.content)
    }

    @Test
    fun editComment_noUser() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        val comment = news.newsComments.first()

        //login as Member
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "test123"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //edit own comment, author not set
        val newsUrl = generateUrl("/api/news/comment/${comment.id}")

        val postResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.POST, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                content = "Edited Test comment"
            }, headers)
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, postResponse.statusCode)
    }

    @Test
    fun editComment_foreignComment() {
        //create author, editor and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")
        val editor = userService.createUser(User(null, "test2", "test123", email = "test2@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        val comment = news.newsComments.first()

        //login as editor
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test2", "test123"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //edit foreign comment
        val newsUrl = generateUrl("/api/news/comment/${comment.id}")

        val postResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.POST, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                this.author = UserIdNameDTO.from(editor)
                content = "Edited Test comment"
            }, headers)
        )

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, postResponse.statusCode)
    }

    @Test
    fun editComment_impersonateMember() {
        //create author,editor and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")
        userService.createUser(User(null, "test2", "test123", email = "test2@example.com"))

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        var comment = news.newsComments.first()

        //login as editor
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test2", "test123"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //try to edit comment, act as original author
        val newsUrl = generateUrl("/api/news/comment/${comment.id}")

        val postResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.POST, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                this.author = UserIdNameDTO.from(author)
                content = "Edited Test comment"
            }, headers)
        )

        comment = newsService.getAllNews().first().newsComments.first()

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, postResponse.statusCode)
        Assertions.assertEquals("Test Comment", comment.content)
    }

    @Test
    fun editComment_invalidOldComment() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        val news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        //login as Member
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "test123"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //edit own comment
        val newsUrl = generateUrl("/api/news/comment/-1")

        val postResponse = restTemplate.exchange<Unit>(
            newsUrl, HttpMethod.POST, HttpEntity<NewsCommentDTO>(NewsCommentDTO().apply {
                this.author = UserIdNameDTO.from(author)
                content = "Edited Test comment"
            }, headers)
        )

        val comment = newsService.getAllNews().first().newsComments.first()

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, postResponse.statusCode)
        Assertions.assertEquals("Test Comment", comment.content)
    }

    @Test
    fun deleteNews_asAdmin() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //delete news
        val newsUrl = generateUrl("/api/news/${news?.id}")

        val response =
            restTemplate.exchange(newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        news = newsService.getLast(1).firstOrNull()

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNull(news)
    }

    @Test
    fun deleteNews_asMod() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MODERATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //delete news
        val newsUrl = generateUrl("/api/news/${news?.id}")

        val response =
            restTemplate.exchange(newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        news = newsService.getLast(1).firstOrNull()

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        Assertions.assertNotNull(news)
    }

    @Test
    fun deleteNews_asMemeber() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        val author = userService.getAll().first()

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getLast(1).firstOrNull()

        Assertions.assertNotNull(news)

        //delete news
        val newsUrl = generateUrl("/api/news/${news?.id}")

        val response =
            restTemplate.exchange(newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        news = newsService.getLast(1).firstOrNull()

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        Assertions.assertNotNull(news)
    }

    @Test
    fun deleteNews_unknownId() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url, SignUpRequest("test", "123456", "test@example.com"), String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("test", "123456"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //delete news
        val newsUrl = generateUrl("/api/news/-1")

        val response =
            restTemplate.exchange(newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)


        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun deleteComment_asAdmin() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")
        userService.createUser(User(null, "delUser", "delUser123", email = "delUser@example.com"))

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        var comment = news.newsComments.firstOrNull()

        //login as Admin
        mailService.markEMailAsValid("delUser")
        userRoleService.changeRole(User(username = "delUser", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("delUser", "delUser123"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //delete comment
        val newsUrl = generateUrl("/api/news/comment/${comment?.id}")

        val postResponse = restTemplate.exchange(
            newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java
        )

        comment = newsService.getAllNews().first().newsComments.firstOrNull()

        Assertions.assertEquals(HttpStatus.OK, postResponse.statusCode)
        Assertions.assertNull(comment?.content)
    }

    @Test
    fun deleteComment_asMod() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")
        userService.createUser(User(null, "delUser", "delUser123", email = "delUser@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        var comment = news.newsComments.firstOrNull()

        //login as Mod
        mailService.markEMailAsValid("delUser")
        userRoleService.changeRole(User(username = "delUser", password = "", email = ""), Role.MODERATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, SignUpRequest("delUser", "delUser123", "delUser@example.com"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //delete comment
        val newsUrl = generateUrl("/api/news/comment/${comment?.id}")

        val postResponse = restTemplate.exchange(
            newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java
        )

        comment = newsService.getAllNews().first().newsComments.firstOrNull()

        Assertions.assertEquals(HttpStatus.OK, postResponse.statusCode)
        Assertions.assertNull(comment?.content)
    }

    @Test
    fun deleteComment_asMemeber_own() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        var comment = news.newsComments.firstOrNull()

        //login as Member
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, SignUpRequest("test", "test123", "test@example.com"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //delete own comment
        val newsUrl = generateUrl("/api/news/comment/${comment?.id}")

        val postResponse = restTemplate.exchange(
            newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java
        )

        comment = newsService.getAllNews().first().newsComments.firstOrNull()

        Assertions.assertEquals(HttpStatus.OK, postResponse.statusCode)
        Assertions.assertNull(comment?.content)
    }

    @Test
    fun deleteComment_asMemeber_foreign() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")
        userService.createUser(User(null, "delUser", "delUser123", email = "delUser@example.com"))

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        var news = newsService.getAllNews().first()
        newsService.addCommentToNews(news.id ?: -1, NewsCommentDTO.from(NewsComment().apply {
            this.author = author
            this.content = "Test Comment"
        }))

        news = newsService.getAllNews().first()
        var comment = news.newsComments.firstOrNull()

        //login as Mod
        mailService.markEMailAsValid("delUser")
        userRoleService.changeRole(User(username = "delUser", password = "", email = ""), Role.MEMBER)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, SignUpRequest("delUser", "delUser123", "delUser@example.com"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //delete comment
        val newsUrl = generateUrl("/api/news/comment/${comment?.id}")

        val postResponse = restTemplate.exchange(
            newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java
        )

        comment = newsService.getAllNews().first().newsComments.firstOrNull()

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, postResponse.statusCode)
        Assertions.assertNotNull(comment?.content)
    }

    @Test
    fun deleteComment_unknownId() {
        //create author and news
        val author = userService.createUser(User(null, "test", "test123", email = "test@example.com"))
            ?: fail("Couldn't create User")
        userService.createUser(User(null, "delUser", "delUser123", email = "delUser@example.com"))
            ?: fail("Couldn't create User")

        newsService.createNews(NewsEntryDTO.from(NewsEntry().apply {
            subject = "News Subject"
            content = "News Content"
            this.author = author
        }))

        //login as Admin
        mailService.markEMailAsValid("delUser")
        userRoleService.changeRole(User(username = "delUser", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2, LoginRequest("delUser", "delUser123"), JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }

        //delete comment
        val newsUrl = generateUrl("/api/news/comment/-1")

        val postResponse = restTemplate.exchange(
            newsUrl, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java
        )

        val comment = newsService.getAllNews().first().newsComments.firstOrNull()

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, postResponse.statusCode)
        Assertions.assertNull(comment?.content)
    }
}
