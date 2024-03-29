package org.darkSolace.muse.lastSeen.model

import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import java.util.*

/**
 * The [LastSeenEntry] class containing visitor session information, a user if the visitor is
 * logged in as well a timestamp when the visitor was last seen.
 */
@Entity
class LastSeenEntry {
    @Id
    @GeneratedValue
    val id: Long = 0

    @OneToOne
    var user: User? = null

    @Temporal(TemporalType.TIMESTAMP)
    var lastSeen: Date = Date()
    var jSession: String? = null
}
