package org.darkSolace.muse.story.model.dto

import org.darkSolace.muse.story.model.Banner
import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.story.model.ChapterComment
import org.darkSolace.muse.story.model.Story
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class ChapterDTO {
    var id: Long? = null
    var title: String = ""
    var summary: String = ""
    var startNotes: String? = ""
    var endNotes: String? = ""
    var hitCount: Long = 0
    var wordCount: Long = 0
    var content: String = ""
    var publishedDate: Date = Date()
    var updatedDate: Date = Date()
    var beta: List<UserIdNameDTO> = emptyList()
    var artist: List<UserIdNameDTO> = emptyList()
    var storyBanner: Banner? = null
    var story: StoryDTO? = null
    var comments: List<ChapterCommentDTO> = emptyList()
    var kudos: List<UserIdNameDTO> = emptyList()

    companion object {
        fun fromList(list: List<Chapter>) = list.map { ChapterDTO.from(it) }
        fun from(chapter: Chapter) = ChapterDTO().apply {
            this.id = chapter.id
            this.title = chapter.title
            this.summary = chapter.summary
            this.startNotes = chapter.startNotes
            this.endNotes = chapter.endNotes
            this.hitCount = chapter.hitCount
            this.wordCount = chapter.wordCount
            this.content = chapter.content
            this.publishedDate = chapter.publishedDate
            this.updatedDate = chapter.updatedDate
            this.beta = UserIdNameDTO.fromList(chapter.beta)
            this.artist = UserIdNameDTO.fromList(chapter.artist)
            this.storyBanner = chapter.storyBanner
            this.story = chapter.story?.let { s -> StoryDTO.from(s) }
            this.comments = ChapterCommentDTO.fromList(chapter.comments)
            this.kudos = UserIdNameDTO.fromList(chapter.kudos)
        }
    }
}
