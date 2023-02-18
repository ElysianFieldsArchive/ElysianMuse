package org.darkSolace.muse.mail.model

import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.util.*

/**
 * Represents a queued email to be sent at the next scheduled opportunity
 */
@Entity
class MailQueueEntry(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @OneToOne
    @Cascade(CascadeType.ALL)
    val mail: Mail? = null,

    @Temporal(TemporalType.TIMESTAMP)
    val enqueueDate: Date = Date(),
)
