package org.darkSolace.muse.story.model.dto

import jakarta.persistence.*
import org.darkSolace.muse.story.model.*
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class StoryDTO {
    var id: Long? = null
    var title: String = ""
    var summary: String = ""
    var storyNotes: String = ""
    var goalChapterCount: Long = 0
    var completed: Boolean = false
    var commentModeration: Boolean = false
    var eventId: Long? = null
    var author: List<UserIdNameDTO> = emptyList()
    var beta: List<UserIdNameDTO> = emptyList()
    var artist: List<UserIdNameDTO> = emptyList()
    var publishedDate: Date = Date()
    var rating: Rating = Rating.ADULT_ONLY
    var storyBanner: Banner? = null
    var chapters: List<ChapterDTO> = emptyList()
    var storyTags: List<StoryTag> = emptyList()
    var hitCount = chapters.sumOf { it.hitCount }
    var wordCount = chapters.sumOf { it.wordCount }
    var kudoCount = chapters.sumOf { it.kudos.count() }
    var favorites: List<UserIdNameDTO> = emptyList()
    var favouritesCount = favorites.count()

    companion object {
        fun fromList(list: List<Story>): List<StoryDTO> = list.map { from(it) }
        fun from(story: Story) = StoryDTO().apply {
            this.id = story.id
            this.title = story.title
            this.summary = story.summary
            this.storyNotes = story.storyNotes
            this.goalChapterCount = story.goalChapterCount
            this.completed = story.completed
            this.commentModeration = story.commentModeration
            this.eventId = story.eventId
            this.author = UserIdNameDTO.fromList(story.author)
            this.beta = UserIdNameDTO.fromList(story.beta)
            this.artist = UserIdNameDTO.fromList(story.artist)
            this.publishedDate = story.publishedDate
            this.rating = story.rating
            this.storyBanner = story.storyBanner
            this.chapters = ChapterDTO.fromList(story.chapters)
            this.storyTags = story.storyTags
            this.favorites = UserIdNameDTO.fromList(story.favorites)
        }
    }
}