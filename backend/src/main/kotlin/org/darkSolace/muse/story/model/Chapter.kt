package org.darkSolace.muse.story.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.Basic
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.darkSolace.muse.user.model.User
import java.util.*

@Entity
class Chapter {
    @Id
    @GeneratedValue
    var id: Long? = null

    var title: String = ""
    var summary: String = ""
    var startNotes: String? = ""
    var endNotes: String? = ""
    val hitCount: Long = 0
    @Lob
    @Basic(fetch = FetchType.LAZY)
    var content: String = ""

    val wordCount: Int = content.split(" ").size

    @Temporal(TemporalType.TIMESTAMP)
    var publishedDate: Date = Date()

    @Temporal(TemporalType.TIMESTAMP)
    var updatedDate: Date = Date()

    @ManyToMany(fetch = FetchType.LAZY)
    var beta: MutableSet<User> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY)
    var artist: MutableSet<User> = mutableSetOf()

    @OneToOne(optional = true)
    var storyBanner: Banner? = null

    var storyId: Long? = null

    @OneToMany(fetch = FetchType.LAZY)
    var comments: MutableSet<ChapterComment> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    val kudos: MutableSet<User> = mutableSetOf()

    @Transient
    @JsonInclude
    val kudoCount = kudos.count()
}
