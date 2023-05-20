package org.darkSolace.muse.privMessages.controller

import jakarta.validation.Valid
import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.privMessages.model.dto.PrivateMessageDTO
import org.darkSolace.muse.privMessages.repository.PrivateMessageRepository
import org.darkSolace.muse.privMessages.service.PrivateMessageService
import org.darkSolace.muse.security.model.UserDetails
import org.darkSolace.muse.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * RestController defining endpoints regarding private messages between users
 */
@RestController
@RequestMapping("/api/message")
@Validated
class PrivateMessageController(
    @Autowired val privateMessageService: PrivateMessageService,
    @Autowired val privateMessageRepository: PrivateMessageRepository,
) {
    /**
     * Retrieves all retrieved private messages for the specified user. Only accessible for the own user.
     *
     * @sample `curl localhost:8080/api/message/5`
     * @param user the user which received messages are retrieved
     * @param authentication the authentication used for this request, to verify the right user is logged in
     * @return list of private messages or HTTP 401 Unauthorized if messages for a different user are retrieved
     */
    @GetMapping("/{user}")
    fun getReceivedMessages(
        @PathVariable @Valid user: User,
        authentication: Authentication?
    ): ResponseEntity<Collection<PrivateMessageDTO>> {
        val userDetails = (authentication?.principal as UserDetails?)
        return if (userDetails?.user == user) {
            val messages =
                PrivateMessageDTO.fromCollection(privateMessageService.getReceivedMessagesForUser(user))
            ResponseEntity.ok(messages)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    /**
     * Retrieves all sent private messages for the specified user. Only accessible for the own user.
     *
     * @sample `curl localhost:8080/api/message/5/sent`
     * @param user the user which sent messages are retrieved
     * @param authentication the authentication used for this request, to verify the right user is logged in
     * @return list of private messages or HTTP 401 Unauthorized if messages for a different user are retrieved
     */
    @GetMapping("/{user}/sent")
    fun getSentMessages(
        @PathVariable @Valid user: User,
        authentication: Authentication?
    ): ResponseEntity<Collection<PrivateMessageDTO>> {
        val userDetails = (authentication?.principal as UserDetails?)
        return if (userDetails?.user == user) {
            val messages =
                PrivateMessageDTO.fromCollection(privateMessageService.getSentMessagesByUser(user))
            ResponseEntity.ok(messages)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    /**
     * Marks a private message as read
     *
     * @sample `curl localhost:8080/api/message/read/56`
     * @param id id of the message to be marked as read
     * @param authentication the authentication used for this request, to verify the right user is logged in
     * @return HTTP OK or UNAUTHORIZED, depending on the requests success
     */
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

    /**
     * Sends a message to another user
     *
     * @sample `curl -X POST -d '...' localhost:8080/api/message/send`
     * @param message the private message to be sent, passed as the request body
     * @param authentication the authentication used for this request, to verify the right user is logged in
     * @return HTTP OK or UNAUTHORIZED, depending on the requests success
     */
    @PostMapping("/send")
    fun sendMessage(
        @RequestBody @Valid message: PrivateMessage,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val userDetails = authentication?.principal as UserDetails?
        return if (userDetails?.user == message.sender) {
            privateMessageService.sendMessage(message)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    /**
     * Deletes a private message
     *
     * @sample `curl -X DELETE localhost:8080/api/message/56`
     * @param message the private message to be deleted
     * @param authentication the authentication used for this request, to verify the right user is logged in
     */
    @DeleteMapping("/{message}")
    fun deleteMessage(
        @PathVariable @Valid message: PrivateMessage,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val userDetails = authentication?.principal as UserDetails?
        val user = userDetails?.user
        val receiverAllowed = message.direction == MessageDirection.INCOMING && user == message.recipient
        val senderAllowed = message.direction == MessageDirection.OUTGOING && user == message.sender
        return if (receiverAllowed || senderAllowed) {
            privateMessageService.deleteMessage(message)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    /**
     * Retrieves the number of unread messages for the given user. The user is derived from the authentication used
     *
     * @sample `curl localhost:8080/api/message/unread`
     * @param authentication the authentication used for this request, to verify the right user is logged in
     * @return number of unread messages
     */
    @GetMapping("/unread")
    fun numberOfUnreadMessages(authentication: Authentication?): ResponseEntity<Long> {
        val userDetails = authentication?.principal as UserDetails?
        val user = userDetails?.user ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return ResponseEntity.ok(privateMessageService.getNumberOfUnreadMessages(user))
    }
}
