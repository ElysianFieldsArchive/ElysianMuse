package org.darkSolace.muse.story.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.util.*
import kotlin.jvm.Transient

@Entity
class Story {
    @Id
    @GeneratedValue
    var id: Long? = null

    var title: String = ""
    var summary: String = ""
    var storyNotes: String = ""
    var goalChapterCount: Long = 0
    var completed: Boolean = false
    var commentModeration: Boolean = false
    var eventId: Long? = null

    @ManyToMany(fetch = FetchType.EAGER)
    var author: MutableSet<User> = mutableSetOf()

    @ManyToMany(fetch = FetchType.EAGER)
    var beta: MutableSet<User> = mutableSetOf()

    @ManyToMany(fetch = FetchType.EAGER)
    var artist: MutableSet<User> = mutableSetOf()

    @Temporal(TemporalType.TIMESTAMP)
    var publishedDate: Date = Date()

    @Enumerated
    var rating: Rating = Rating.ADULT_ONLY

    @OneToOne(optional = true)
    var storyBanner: Banner? = null

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.DELETE)
    var chapters: MutableSet<Chapter> = mutableSetOf()

    @ManyToMany(fetch = FetchType.EAGER)
    var storyTags: MutableSet<StoryTag> = mutableSetOf()

    @Transient
    @JsonInclude
    var hitCount = chapters.sumOf { it.hitCount }

    @Transient
    @JsonInclude
    var wordCount = chapters.sumOf { it.wordCount }

    @Transient
    @JsonInclude
    var kudoCount = chapters.sumOf { it.kudoCount }

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    var favorites: MutableList<User> = mutableListOf()

    @Transient
    @JsonInclude
    var favouritesCount = favorites.count()
}
