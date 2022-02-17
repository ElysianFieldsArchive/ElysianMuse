package org.darkSolace.muse.user.model

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
    var acceptedDate: Date? = null,
    var confirmationDetails: String? = null
)

