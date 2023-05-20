package org.darkSolace.muse.news.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
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

    @ManyToOne
    lateinit var author: User

    @Lob
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Date = Date()
}
