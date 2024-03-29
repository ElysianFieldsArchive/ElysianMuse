package org.darkSolace.muse.news.model

import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import java.util.*

/**
 * The [NewsComment] model class
 *
 * Holds all values containing to a comment posted on a news entry.
 */
@Entity
class NewsComment {
    @Id
    @GeneratedValue
    var id: Long? = null

    @OneToOne
    var author: User? = null

    @Column(length = 3000)
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Date = Date()
}
