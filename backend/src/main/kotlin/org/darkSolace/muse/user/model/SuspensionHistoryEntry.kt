package org.darkSolace.muse.user.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.util.*

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

