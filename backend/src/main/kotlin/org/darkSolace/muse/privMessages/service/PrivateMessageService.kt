package org.darkSolace.muse.privMessages.service

import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.privMessages.repository.PrivateMessageRepository
import org.darkSolace.muse.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PrivateMessageService(@Autowired val privateMessageRepository: PrivateMessageRepository) {
    fun getReceivedMessagesForUser(user: User) =
        privateMessageRepository.findAllByRecipientAndDirection(user, MessageDirection.INCOMING)

    fun getSentMessagesByUser(user: User): List<PrivateMessage> =
        privateMessageRepository.findAllBySenderAndDirection(user, MessageDirection.OUTGOING)

    fun markMessageAsRead(message: PrivateMessage) {
        message.isRead = true
        privateMessageRepository.save(message)
    }

    fun sendMessage(sentMessage: PrivateMessage) {
        sentMessage.isRead = true
        val receivingMessage = sentMessage.copy(direction = MessageDirection.INCOMING, isRead = false)
        privateMessageRepository.save(sentMessage)
        privateMessageRepository.save(receivingMessage)
    }

    fun getNumberOfUnreadMessages(user: User) = privateMessageRepository.countByRecipientAndIsReadIsFalse(user)

    fun deleteMessage(message: PrivateMessage) {
        privateMessageRepository.delete(message)
    }
}
