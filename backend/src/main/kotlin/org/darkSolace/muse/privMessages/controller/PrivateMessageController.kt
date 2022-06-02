package org.darkSolace.muse.privMessages.controller

import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.privMessages.repository.PrivateMessageRepository
import org.darkSolace.muse.privMessages.service.PrivateMessageService
import org.darkSolace.muse.security.model.UserDetails
import org.darkSolace.muse.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/message")
class PrivateMessageController(
    @Autowired val privateMessageService: PrivateMessageService,
    @Autowired val privateMessageRepository: PrivateMessageRepository,
) {

    @GetMapping("/{user}")
    fun getReceivedMessages(
        @PathVariable user: User,
        authentication: Authentication?
    ): ResponseEntity<List<PrivateMessage>> {
        val userDetails = (authentication?.principal as UserDetails?)
        return if (userDetails?.user == user) {
            val messages = privateMessageService.getReceivedMessagesForUser(user)
            ResponseEntity.ok(messages)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @GetMapping("/{user}/sent")
    fun getSentMessages(
        @PathVariable user: User,
        authentication: Authentication?
    ): ResponseEntity<List<PrivateMessage>> {
        val userDetails = (authentication?.principal as UserDetails?)
        return if (userDetails?.user == user) {
            val messages = privateMessageService.getSentMessagesByUser(user)
            ResponseEntity.ok(messages)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/read/{id}")
    fun markMessageAsRead(@PathVariable id: Long, authentication: Authentication?): ResponseEntity<Unit> {
        val message =
            privateMessageRepository.findByIdOrNull(id) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val userDetails = authentication?.principal as UserDetails?
        val user = userDetails?.user
        val receiverAllowed = message.direction == MessageDirection.INCOMING && user == message.recipient
        val senderAllowed = message.direction == MessageDirection.OUTGOING && user == message.sender
        return if (receiverAllowed || senderAllowed) {
            privateMessageService.markMessageAsRead(message)
            ResponseEntity.ok().build()
        } else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: PrivateMessage, authentication: Authentication?): ResponseEntity<Unit> {
        val userDetails = authentication?.principal as UserDetails?
        return if (userDetails?.user == message.sender) {
            privateMessageService.sendMessage(message)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @DeleteMapping("/{message}")
    fun deleteMessage(@PathVariable message: PrivateMessage, authentication: Authentication?) {
        val userDetails = authentication?.principal as UserDetails?
        if (userDetails?.user in arrayOf(message.sender, message.recipient)) {
            privateMessageService.deleteMessage(message)
        }
    }

    @GetMapping("/unread")
    fun numberOfUnreadMessages(authentication: Authentication?): ResponseEntity<Long> {
        val userDetails = authentication?.principal as UserDetails?
        val user = userDetails?.user ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return ResponseEntity.ok(privateMessageService.getNumberOfUnreadMessages(user))
    }
}
