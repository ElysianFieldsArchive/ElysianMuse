package org.darkSolace.muse.story.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
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

    @OneToMany
    var author: MutableList<User> = emptyList<User>().toMutableList()

    @OneToMany
    var beta: MutableList<User> = emptyList<User>().toMutableList()

    @OneToMany
    var artist: MutableList<User> = emptyList<User>().toMutableList()

    @Temporal(TemporalType.TIMESTAMP)
    var publishedDate: Date = Date()

    @Enumerated
    var rating: Rating = Rating.ADULT_ONLY

    @OneToOne(optional = true)
    var storyBanner: Banner? = null

    @OneToMany
    var chapters: MutableList<Chapter> = emptyList<Chapter>().toMutableList()

    @ManyToMany
    var storyTags: MutableList<StoryTag> = emptyList<StoryTag>().toMutableList()

    @Transient
    @JsonInclude
    var hitCount = chapters.sumOf { it.hitCount }

    @Transient
    @JsonInclude
    var wordCount = chapters.sumOf { it.wordCount }

    @Transient
    @JsonInclude
    var kudoCount = chapters.sumOf { it.kudoCount }

    @ManyToMany
    @JsonIgnore
    var favorites: MutableList<User> = emptyList<User>().toMutableList()

    @Transient
    @JsonInclude
    var favouritesCount = favorites.count()
}
