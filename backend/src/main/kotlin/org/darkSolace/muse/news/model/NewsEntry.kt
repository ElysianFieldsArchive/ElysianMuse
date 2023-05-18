package org.darkSolace.muse.news.model

import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import java.util.*

/**
 * The [NewsEntry] model class
 *
 * Holds all values containing to a news entry.
 */
@Entity
class NewsEntry {
    @Id
    @GeneratedValue
    var id: Long? = null

    @OneToOne
    lateinit var author: User

    var subject: String = ""

    @Lob
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Date = Date()

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    var newsComments: MutableList<NewsComment> = mutableListOf()
}
