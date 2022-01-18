package org.darkSolace.muse.userModule.model

import java.util.*
import javax.persistence.*

@Entity
class SuspensionHistoryEntry(
    @GeneratedValue
    @Id
    val id: Long?,
    @OneToOne
    val user: User,
    @Temporal(TemporalType.TIMESTAMP)
    val suspendedDate: Date = Date(),
    val confirmationCode: String = UUID.randomUUID().toString(),
    @Temporal(TemporalType.TIMESTAMP)
    var acceptedDate: Date? = null
)

