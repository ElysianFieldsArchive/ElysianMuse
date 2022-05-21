package org.darkSolace.muse.mail.repository

import org.darkSolace.muse.mail.model.MailQueueEntry
import org.springframework.data.repository.CrudRepository

/**
 * CRUD repository for the [MailQueueEntry] entity
 */
interface MailQueueRepository : CrudRepository<MailQueueEntry, Long>
