package org.darkSolace.muse.news

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.news.model.dto.NewsEntryDTO
import org.darkSolace.muse.news.service.NewsService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
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
}
