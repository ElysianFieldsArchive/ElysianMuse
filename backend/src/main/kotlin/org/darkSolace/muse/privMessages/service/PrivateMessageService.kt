package org.darkSolace.muse.privMessages.service

import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.privMessages.repository.PrivateMessageRepository
import org.darkSolace.muse.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service class for [PrivateMessage] related tasks.
 */
@Service
class PrivateMessageService(@Autowired val privateMessageRepository: PrivateMessageRepository) {

    /**
     * Retrieves [PrivateMessage]s retrieved by the provided [User]
     *
     * @param user the [User] for which received messages are retrieved
     * @return [List] of [PrivateMessage]s
     */
    fun getReceivedMessagesForUser(user: User) =
        privateMessageRepository.findAllByRecipientAndDirection(user, MessageDirection.INCOMING)

    /**
     * Retrieves [PrivateMessage]s sent by the provided [User]
     *
     * @param user the [User] for which sent messages are retrieved
     * @return [List] of [PrivateMessage]s
     */
    fun getSentMessagesByUser(user: User): List<PrivateMessage> =
        privateMessageRepository.findAllBySenderAndDirection(user, MessageDirection.OUTGOING)

    /**
     * Maks a [PrivateMessage] as read
     *
     * @param message the [PrivateMessage] to be marked as read
     */
    fun markMessageAsRead(message: PrivateMessage) {
        message.isRead = true
        privateMessageRepository.save(message)
    }

    /**
     * Sends a [PrivateMessage] message.
     * Generates two [PrivateMessage]s - on as sent by the sender, on received by the recipient
     *
     * @param sentMessage the [PrivateMessage] to be sent
     */
    fun sendMessage(sentMessage: PrivateMessage) {
        sentMessage.isRead = true
        val receivingMessage = sentMessage.copy(direction = MessageDirection.INCOMING, isRead = false)
        privateMessageRepository.save(sentMessage)
        privateMessageRepository.save(receivingMessage)
    }

    /**
     * Retrieves the number of unread messages for a given [User]
     *
     * @param user the [User] for which the number of unread messages is retrieved
     * @return the number of unread [PrivateMessage]s
     */
    fun getNumberOfUnreadMessages(user: User) = privateMessageRepository.countByRecipientAndIsReadIsFalse(user)

    /**
     * Deletes a [PrivateMessage]
     *
     * @param message the [PrivateMessage] to be deleted
     */
    fun deleteMessage(message: PrivateMessage) {
        //find replies to this message to break the link
        privateMessageRepository.findAllByInReplyTo(message).forEach { childMessage ->
            childMessage.inReplyTo = null
            privateMessageRepository.save(childMessage)
        }

        privateMessageRepository.delete(message)
    }
}
