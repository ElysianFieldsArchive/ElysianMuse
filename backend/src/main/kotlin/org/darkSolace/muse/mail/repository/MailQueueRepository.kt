package org.darkSolace.muse.mail.repository

import org.darkSolace.muse.mail.model.MailQueueEntry
import org.darkSolace.muse.user.model.User
import org.springframework.data.repository.CrudRepository

/**
 * CRUD repository for the [MailQueueEntry] entity
 */
interface MailQueueRepository : CrudRepository<MailQueueEntry, Long> {
    /**
     * Deletes all mails enqueued be sent to the specified [User]
     *
     * @param recipient the [User] which enqueued mails are deleted
     */
    fun deleteAllByMail_Recipient(recipient: User)
}
