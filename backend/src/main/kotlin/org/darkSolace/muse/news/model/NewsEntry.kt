package org.darkSolace.muse.news.model

import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import java.util.*

@Entity
class NewsEntry {
    @Id
    @GeneratedValue
    var id: Long? = null

    @OneToOne
    var author: User? = null

    var subject: String = ""

    @Column(length = 3000)
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Date = Date()

    @OneToMany(fetch = FetchType.EAGER)
    var newsComments : MutableList<NewsComment> = mutableListOf()
}