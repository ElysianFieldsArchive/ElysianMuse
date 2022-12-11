package org.darkSolace.muse.privMessages

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.privMessages.service.PrivateMessageService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

class PrivateMessageApiTests : TestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var privateMessageService: PrivateMessageService

    @Autowired
    private lateinit var mailService: MailService

    @Test
    @Order(1)
    fun getReceivedMessages_OwnUser() {
        val sender = userService.createUser(User(null, "sender", "", email = "sender@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val recipient = userService.getById(signInResponse?.body?.id ?: fail("Couldn't get recipient user"))

        val url3 = generateUrl("/api/message/${recipient?.id}")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response?.body?.size)

        privateMessageService.sendMessage(PrivateMessage(null).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val secondResponse = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, secondResponse.statusCode)
        Assertions.assertEquals(1, secondResponse?.body?.size)
    }

    @Test
    @Order(2)
    fun getReceivedMessages_DifferentUser() {
        val recipient = userService.createUser(User(null, "recipient", "", email = "recipient@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        // Logged in as "test", trying to get messages of "recipient" user
        val url3 = generateUrl("/api/message/${recipient.id}")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(3)
    fun getSentMessages_OwnUser() {
        val recipient = userService.createUser(User(null, "recipient", "", email = "recipient@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val sender = userService.getById(signInResponse?.body?.id ?: fail("Couldn't get recipient user"))

        val url3 = generateUrl("/api/message/${sender?.id}/sent")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response?.body?.size)

        privateMessageService.sendMessage(PrivateMessage(null).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val secondResponse = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, secondResponse.statusCode)
        Assertions.assertEquals(1, secondResponse?.body?.size)
    }

    @Test
    @Order(4)
    fun getSentMessages_DifferentUser() {
        val recipient = userService.createUser(User(null, "recipient", "", email = "recipient@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        // Logged in as "test", trying to get messages of "recipient" user
        val url3 = generateUrl("/api/message/${recipient.id}/sent")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(5)
    fun markMessageAsRead_OwnUser() {
        val sender = userService.createUser(User(null, "sender", "", email = "sender@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val recipient = userService.getById(signInResponse?.body?.id ?: fail("Couldn't get recipient user"))


        privateMessageService.sendMessage(PrivateMessage(null).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val url3 = generateUrl("/api/message/${recipient?.id}")
        val secondResponse = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )
        val message = secondResponse?.body?.first() ?: fail("Couldn't get message.")

        Assertions.assertEquals(HttpStatus.OK, secondResponse.statusCode)
        Assertions.assertEquals(1, secondResponse.body?.size ?: -1)
        Assertions.assertFalse(message.isRead)

        val url4 = generateUrl("/api/message/read/${message.id}")
        val thirdResponse = restTemplate.exchange(
            url4,
            HttpMethod.POST,
            HttpEntity<HttpHeaders>(headers),
            Unit::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, thirdResponse.statusCode)

        val forthResponse = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<PrivateMessage>::class.java
        )

        val readMessage = forthResponse.body?.first() ?: fail("Couldn't get read message")

        Assertions.assertTrue(readMessage.isRead)
    }

    @Test
    @Order(6)
    fun markMessageAsRead_DifferentUser() {
        val recipient = userService.createUser(User(null, "recipient", "", email = "recipient@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val sender = userService.getById(signInResponse?.body?.id ?: fail("Couldn't get recipient user"))


        privateMessageService.sendMessage(PrivateMessage(null).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val message = privateMessageService.getReceivedMessagesForUser(recipient).first()

        Assertions.assertFalse(message.isRead)

        val url4 = generateUrl("/api/message/read/${message.id}")
        val thirdResponse = restTemplate.exchange(
            url4,
            HttpMethod.POST,
            HttpEntity<HttpHeaders>(headers),
            Unit::class.java
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, thirdResponse.statusCode)
    }

    @Test
    @Order(7)
    fun sendMessage_OwnUser() {
        val recipient = userService.createUser(User(null, "recipient", "", email = "recipient@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val sender = userService.getById(signInResponse?.body?.id ?: fail("Couldn't retrieve sender"))

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse.body?.token}")
        }

        Assertions.assertEquals(0, privateMessageService.getNumberOfUnreadMessages(recipient))

        val message = PrivateMessage(null, MessageDirection.OUTGOING, sender, recipient, "Test subject", "Test Content")
        val url3 = generateUrl("/api/message/send")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.POST,
            HttpEntity(message, headers),
            Unit::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, privateMessageService.getNumberOfUnreadMessages(recipient))
    }

    @Test
    @Order(8)
    fun sendMessage_DifferentUser() {
        val sender = userService.createUser(User(null, "sender", "", email = "sender@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val recipient = userService.getById(signInResponse?.body?.id ?: fail("Couldn't retrieve sender"))
            ?: fail("Couldn't retrieve sender")

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse.body?.token}")
        }

        Assertions.assertEquals(0, privateMessageService.getNumberOfUnreadMessages(recipient))

        val message = PrivateMessage(null, MessageDirection.OUTGOING, sender, recipient, "Test subject", "Test Content")
        val url3 = generateUrl("/api/message/send")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.POST,
            HttpEntity(message, headers),
            Unit::class.java
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals(0, privateMessageService.getNumberOfUnreadMessages(recipient))
    }

    @Test
    @Order(9)
    fun getNumberOfUnreadMessages() {
        val sender = userService.createUser(User(null, "sender", "", email = "sender@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        val recipient = userService.getById(signInResponse?.body?.id ?: fail("Couldn't get recipient user"))
            ?: fail("Couldn't get recipient user")

        val url3 = generateUrl("/api/message/unread")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Long::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response?.body)

        privateMessageService.sendMessage(PrivateMessage(null).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val secondResponse = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Long::class.java
        )

        Assertions.assertEquals(1, secondResponse?.body)

        val message = privateMessageService.getReceivedMessagesForUser(recipient).first()

        val url4 = generateUrl("/api/message/read/${message.id}")
        restTemplate.exchange(
            url4,
            HttpMethod.POST,
            HttpEntity<HttpHeaders>(headers),
            Unit::class.java
        )

        val forthResponse = restTemplate.exchange(
            url3,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Long::class.java
        )

        Assertions.assertEquals(0, forthResponse?.body)
    }

    @Test
    @Order(10)
    fun getNumberOfUnreadMessages_NotSignedIn() {
        val url = generateUrl("/api/message/unread")
        val response = restTemplate.getForEntity(
            url,
            Unit::class.java
        )

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(11)
    fun deletePrivateMessage() {
        val recipient = userService.createUser(User(null, "recipient", "", email = "recipient@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val sender = userService.getById(signInResponse?.body?.id ?: fail("Couldn't retrieve sender"))
            ?: fail("Couldn't retrieve sender")

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse.body?.token}")
        }

        Assertions.assertEquals(0, privateMessageService.getNumberOfUnreadMessages(recipient))

        val message = PrivateMessage(null, MessageDirection.OUTGOING, sender, recipient, "Test subject", "Test Content")
        val url3 = generateUrl("/api/message/send")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.POST,
            HttpEntity(message, headers),
            Unit::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, privateMessageService.getNumberOfUnreadMessages(recipient))

        val sentMessage = privateMessageService.getSentMessagesByUser(sender)
        val url4 = generateUrl("/api/message/${sentMessage.first().id}")
        val deleteResponse =
            restTemplate.exchange(url4, HttpMethod.DELETE, HttpEntity(null, headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, deleteResponse.statusCode)
        Assertions.assertTrue(privateMessageService.getSentMessagesByUser(sender).isEmpty())
    }

    @Test
    @Order(12)
    fun deletePrivateMessage_differentUser() {
        val recipient = userService.createUser(User(null, "recipient", "", email = "recipient@example.org"))
            ?: fail("Couldn't create sender")
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val sender = userService.getById(signInResponse?.body?.id ?: fail("Couldn't retrieve sender"))
            ?: fail("Couldn't retrieve sender")

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse.body?.token}")
        }

        Assertions.assertEquals(0, privateMessageService.getNumberOfUnreadMessages(recipient))

        val message = PrivateMessage(null, MessageDirection.OUTGOING, sender, recipient, "Test subject", "Test Content")
        val url3 = generateUrl("/api/message/send")
        val response = restTemplate.exchange(
            url3,
            HttpMethod.POST,
            HttpEntity(message, headers),
            Unit::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, privateMessageService.getNumberOfUnreadMessages(recipient))

        //try to delete received message from another user
        val sentMessage = privateMessageService.getReceivedMessagesForUser(recipient)
        val url4 = generateUrl("/api/message/${sentMessage.first().id}")
        val deleteResponse =
            restTemplate.exchange(url4, HttpMethod.DELETE, HttpEntity(null, headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, deleteResponse.statusCode)
        Assertions.assertTrue(privateMessageService.getNumberOfUnreadMessages(recipient) != 0L)
    }
}
