package org.darkSolace.muse.story.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import java.util.*
import kotlin.jvm.Transient

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
    val wordCount: Long = 0

    @Lob
    @Basic(fetch = FetchType.LAZY)
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var publishedDate: Date = Date()

    @Temporal(TemporalType.TIMESTAMP)
    val updatedDate: Date = Date()

    @OneToMany
    var beta: MutableList<User> = emptyList<User>().toMutableList()

    @OneToMany
    var artist: MutableList<User> = emptyList<User>().toMutableList()

    @OneToOne(optional = true)
    var storyBanner: Banner? = null

    @OneToOne(fetch = FetchType.LAZY)
    var story: Story? = null

    @OneToMany
    var comments: MutableList<ChapterComment> = emptyList<ChapterComment>().toMutableList()

    @ManyToMany
    @JsonIgnore
    val kudos: MutableList<User> = emptyList<User>().toMutableList()

    @Transient
    @JsonInclude
    val kudoCount = kudos.count()
}
