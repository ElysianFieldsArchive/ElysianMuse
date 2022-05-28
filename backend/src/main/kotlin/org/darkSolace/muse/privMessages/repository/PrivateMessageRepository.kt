package org.darkSolace.muse.privMessages.repository

import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface PrivateMessageRepository : CrudRepository<PrivateMessage, Long> {
    @Transactional
    fun findAllByRecipientAndDirection(recipient: User, direction: MessageDirection): List<PrivateMessage>

    @Transactional
    fun findAllBySenderAndDirection(sender: User, direction: MessageDirection): List<PrivateMessage>
    fun countByRecipientAndIsReadIsFalse(recipient: User): Long

    @Transactional
    fun findAllByInReplyTo(reply: PrivateMessage): List<PrivateMessage>
}
