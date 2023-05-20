package org.darkSolace.muse.news.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
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

    @ManyToOne
    lateinit var author: User

    var subject: String = ""

    @Lob
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Date = Date()

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    var newsComments: MutableList<NewsComment> = mutableListOf()
}
