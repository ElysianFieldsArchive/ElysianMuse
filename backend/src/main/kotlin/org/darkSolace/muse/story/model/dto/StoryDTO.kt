package org.darkSolace.muse.story.model.dto

import org.darkSolace.muse.DTO
import org.darkSolace.muse.story.model.Banner
import org.darkSolace.muse.story.model.Rating
import org.darkSolace.muse.story.model.Story
import org.darkSolace.muse.story.model.StoryTag
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
    var author: Set<UserIdNameDTO> = emptySet()
    var beta: Set<UserIdNameDTO> = emptySet()
    var artist: Set<UserIdNameDTO> = emptySet()
    var publishedDate: Date = Date()
    var rating: Rating = Rating.ADULT_ONLY
    var storyBanner: Banner? = null
    var chapters: List<ChapterDTO> = emptyList()
    var storyTags: Set<StoryTag> = emptySet()
    var hitCount = chapters.sumOf { it.hitCount }
    var wordCount = chapters.sumOf { it.wordCount }
    var kudoCount = chapters.sumOf { it.kudos.count() }
    var favorites: Set<UserIdNameDTO> = emptySet()
    var favouritesCount = favorites.count()

    companion object : DTO<Story, StoryDTO> {
        override fun from(item: Story) = StoryDTO().apply {
            this.id = item.id
            this.title = item.title
            this.summary = item.summary
            this.storyNotes = item.storyNotes
            this.goalChapterCount = item.goalChapterCount
            this.completed = item.completed
            this.commentModeration = item.commentModeration
            this.eventId = item.eventId
            this.author = UserIdNameDTO.fromCollection(item.author).toSet()
            this.beta = UserIdNameDTO.fromCollection(item.beta).toSet()
            this.artist = UserIdNameDTO.fromCollection(item.artist).toSet()
            this.publishedDate = item.publishedDate
            this.rating = item.rating
            this.storyBanner = item.storyBanner
            this.chapters = ChapterDTO.fromCollection(item.chapters).toList()
            this.storyTags = item.storyTags
            this.favorites = UserIdNameDTO.fromCollection(item.favorites).toSet()
        }
    }
}