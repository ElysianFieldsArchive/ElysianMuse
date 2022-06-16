package org.darkSolace.muse.privMessages.repository

import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional


/**
 * CRUD-Repository for the [PrivateMessage] entity
 */
interface PrivateMessageRepository : CrudRepository<PrivateMessage, Long> {
    /**
     * Retrieves all [PrivateMessage]s for a given [User] as recipient.
     * Either send or received depending on the [MessageDirection]
     *
     * @param recipient the [User] entered as recipient in the messages to be received
     * @param direction the [MessageDirection] to retrieve sent or received messages
     * @return a [List] of [PrivateMessage]s
     */
    @Transactional
    fun findAllByRecipientAndDirection(recipient: User, direction: MessageDirection): List<PrivateMessage>

    /**
     * Retrieves all [PrivateMessage]s for a given [User] as sender.
     * Either send or received depending on the [MessageDirection]
     *
     * @param sender the [User] entered as sender in the messages to be received
     * @param direction the [MessageDirection] to retrieve sent or received messages
     * @return a [List] of [PrivateMessage]s
     */
    @Transactional
    fun findAllBySenderAndDirection(sender: User, direction: MessageDirection): List<PrivateMessage>

    /**
     * Retrieves the number of unread messassed for a given [User] entered as recipient.
     *
     * @param recipient the [User], entered as recipient, for which unread messages should be counted.
     * @return the number of unread messages
     */
    fun countByRecipientAndIsReadIsFalse(recipient: User): Long

    /**
     * Retrieves all messages in reply to a given [PrivateMessage]
     *
     * @param reply the [PrivateMessage] for which replies are retrieves
     * @return a [List] of [PrivateMessage]s in reply to the given [PrivateMessage]
     */
    @Transactional
    fun findAllByInReplyTo(reply: PrivateMessage): List<PrivateMessage>
}
