package org.darkSolace.muse.mail.model

import org.springframework.mail.SimpleMailMessage
import java.util.*
import javax.persistence.*

/**
 * Represents a queued email to be sent at the next scheduled opportunity
 */
@Entity
class MailQueueEntry(
    @Id
    @GeneratedValue
    val id: Long? = null,

    val mail: SimpleMailMessage? = null,

    @Temporal(TemporalType.TIMESTAMP)
    val enqueueDate: Date = Date(),
)
