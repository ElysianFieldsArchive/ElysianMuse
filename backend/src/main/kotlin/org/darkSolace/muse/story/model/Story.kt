package org.darkSolace.muse.story.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.darkSolace.muse.user.model.User
import java.util.*

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

    @ManyToMany(fetch = FetchType.LAZY)
    var author: MutableSet<User> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY)
    var beta: MutableSet<User> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY)
    var artist: MutableSet<User> = mutableSetOf()

    @Temporal(TemporalType.TIMESTAMP)
    var publishedDate: Date = Date()

    @Enumerated
    var rating: Rating = Rating.ADULT_ONLY

    @OneToOne(optional = true)
    var storyBanner: Banner? = null

    @OneToMany(fetch = FetchType.EAGER)
//    @Cascade(CascadeType.DELETE)
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
