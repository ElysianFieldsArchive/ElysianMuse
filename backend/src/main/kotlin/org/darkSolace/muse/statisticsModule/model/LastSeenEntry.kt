package org.darkSolace.muse.statisticsModule.model

import org.darkSolace.muse.userModule.model.User
import java.util.*
import javax.persistence.*

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
