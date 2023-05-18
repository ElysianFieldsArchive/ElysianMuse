package org.darkSolace.muse.privMessages

import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.privMessages.repository.PrivateMessageRepository
import org.darkSolace.muse.privMessages.service.PrivateMessageService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired

class PrivateMessageServiceTests : TestBase() {
    @Autowired
    lateinit var privateMessageService: PrivateMessageService

    @Autowired
    lateinit var privateMessageRepository: PrivateMessageRepository

    @Autowired
    lateinit var userService: UserService

    @Test
    @Order(1)
    fun sendMessageTest() {
        val sender = userService.createUser(User(null, "test", "", email = "test@example.org"))
            ?: fail("Couldn't create sender")
        val recipient = userService.createUser(User(null, "test2", "", email = "test2@example.org"))
            ?: fail("Couldn't create recipient")

        privateMessageService.sendMessage(PrivateMessage(sender = sender, recipient = recipient).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })


        Assertions.assertEquals(2, privateMessageRepository.count())
        Assertions.assertEquals(1, privateMessageRepository.countByRecipientAndIsReadIsFalse(recipient))
        Assertions.assertTrue(
            privateMessageRepository.findAllBySenderAndDirection(
                sender,
                MessageDirection.OUTGOING
            ).isNotEmpty()
        )
        Assertions.assertTrue(
            privateMessageRepository.findAllBySenderAndDirection(
                sender,
                MessageDirection.INCOMING
            ).isNotEmpty()
        )
        Assertions.assertTrue(
            privateMessageRepository.findAllByRecipientAndDirection(
                recipient,
                MessageDirection.OUTGOING
            ).isNotEmpty()
        )
        Assertions.assertTrue(
            privateMessageRepository.findAllByRecipientAndDirection(
                recipient,
                MessageDirection.INCOMING
            ).isNotEmpty()
        )

    }

    @Test
    @Order(2)
    fun receiveMessage() {
        val sender = userService.createUser(User(null, "test", "", email = "test@example.org"))
            ?: fail("Couldn't create sender")
        val recipient = userService.createUser(User(null, "test2", "", email = "test2@example.org"))
            ?: fail("Couldn't create recipient")

        privateMessageService.sendMessage(PrivateMessage(sender = sender, recipient = recipient).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val messages = privateMessageService.getReceivedMessagesForUser(recipient)
        val message = messages.first()

        Assertions.assertEquals(1, messages.count())
        Assertions.assertEquals(sender, message.sender)
        Assertions.assertEquals(recipient, message.recipient)
        Assertions.assertEquals("Test private message", message.subject)
        Assertions.assertEquals("Test private message content", message.content)
    }

    @Test
    @Order(3)
    fun trackSentMessages() {
        val sender = userService.createUser(User(null, "test", "", email = "test@example.org"))
            ?: fail("Couldn't create sender")
        val recipient = userService.createUser(User(null, "test2", "", email = "test2@example.org"))
            ?: fail("Couldn't create recipient")

        privateMessageService.sendMessage(PrivateMessage(sender = sender, recipient = recipient).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val messages = privateMessageService.getSentMessagesByUser(sender)
        val message = messages.first()

        Assertions.assertEquals(1, messages.count())
        Assertions.assertEquals(sender, message.sender)
        Assertions.assertEquals(recipient, message.recipient)
        Assertions.assertEquals("Test private message", message.subject)
        Assertions.assertEquals("Test private message content", message.content)
    }

    @Test
    @Order(4)
    fun markMessageAsRead() {
        val sender = userService.createUser(User(null, "test", "", email = "test@example.org"))
            ?: fail("Couldn't create sender")
        val recipient = userService.createUser(User(null, "test2", "", email = "test2@example.org"))
            ?: fail("Couldn't create recipient")

        privateMessageService.sendMessage(PrivateMessage(sender = sender, recipient = recipient).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val messages = privateMessageService.getReceivedMessagesForUser(recipient)
        val message = messages.first()

        Assertions.assertEquals(1, messages.count())
        Assertions.assertFalse(message.isRead)

        privateMessageService.markMessageAsRead(message)

        val readMessage = privateMessageService.getReceivedMessagesForUser(recipient).first()

        Assertions.assertTrue(readMessage.isRead)
    }

    @Test
    @Order(5)
    fun deleteMessage() {
        val sender = userService.createUser(User(null, "test", "", email = "test@example.org"))
            ?: fail("Couldn't create sender")
        val recipient = userService.createUser(User(null, "test2", "", email = "test2@example.org"))
            ?: fail("Couldn't create recipient")

        privateMessageService.sendMessage(PrivateMessage(sender = sender, recipient = recipient).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        })

        val messages = privateMessageService.getReceivedMessagesForUser(recipient)
        val message = messages.first()

        Assertions.assertEquals(1, messages.count())

        privateMessageService.deleteMessage(message)

        Assertions.assertEquals(0, privateMessageService.getReceivedMessagesForUser(recipient).count())
    }

    @Test
    @Order(6)
    fun deleteMessageWithReply() {
        val sender = userService.createUser(User(null, "test", "", email = "test@example.org"))
            ?: fail("Couldn't create sender")
        val recipient = userService.createUser(User(null, "test2", "", email = "test2@example.org"))
            ?: fail("Couldn't create recipient")

        val originalMessage = PrivateMessage(sender = sender, recipient = recipient).apply {
            this.sender = sender
            this.recipient = recipient
            this.subject = "Test private message"
            this.content = "Test private message content"
        }

        privateMessageService.sendMessage(originalMessage)

        val messages = privateMessageService.getReceivedMessagesForUser(recipient)
        val fetchedMessage = messages.first() //getting the original message
        Assertions.assertEquals(1, messages.count())

        val replyMessage = PrivateMessage(sender = sender, recipient = recipient).apply {
            this.sender = recipient
            this.recipient = sender
            this.subject = "Reply Test private message"
            this.content = "Reply Test private message content"
            this.inReplyTo = fetchedMessage
        }
        privateMessageService.sendMessage(replyMessage)


        privateMessageService.deleteMessage(fetchedMessage)
        Assertions.assertEquals(0, privateMessageService.getReceivedMessagesForUser(recipient).count())

        val repliedMessage = privateMessageService.getSentMessagesByUser(recipient).first()

        Assertions.assertNull(repliedMessage.inReplyTo)
    }
}
