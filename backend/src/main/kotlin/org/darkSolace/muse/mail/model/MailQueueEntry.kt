package org.darkSolace.muse.mail.model

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.util.*
import javax.persistence.*

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