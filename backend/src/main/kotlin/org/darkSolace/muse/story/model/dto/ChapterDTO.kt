package org.darkSolace.muse.story.model.dto

import jakarta.validation.constraints.NotEmpty
import org.darkSolace.muse.DTO
import org.darkSolace.muse.story.model.Banner
import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class ChapterDTO {
    var id: Long? = null
    var title: String = ""
    var summary: String = ""
    var startNotes: String? = ""
    var endNotes: String? = ""
    var hitCount: Long = 0
    var wordCount: Int = 0

    @NotEmpty
    var content: String = ""
    var publishedDate: Date = Date()
    var updatedDate: Date = Date()
    var beta: Set<UserIdNameDTO> = emptySet()
    var artist: Set<UserIdNameDTO> = emptySet()
    var storyBanner: Banner? = null
    var storyId: Long? = 0
    var comments: List<ChapterCommentDTO> = emptyList()
    var kudos: Set<UserIdNameDTO> = emptySet()

    companion object : DTO<Chapter, ChapterDTO> {
        override fun from(item: Chapter) = ChapterDTO().apply {
            this.id = item.id
            this.title = item.title
            this.summary = item.summary
            this.startNotes = item.startNotes
            this.endNotes = item.endNotes
            this.hitCount = item.hitCount
            this.wordCount = item.wordCount
            this.content = item.content
            this.publishedDate = item.publishedDate
            this.updatedDate = item.updatedDate
            this.beta = UserIdNameDTO.fromCollection(item.beta).toSet()
            this.artist = UserIdNameDTO.fromCollection(item.artist).toSet()
            this.storyBanner = item.storyBanner
            this.storyId = item.storyId
            this.comments = ChapterCommentDTO.fromCollection(item.comments)
            this.kudos = UserIdNameDTO.fromCollection(item.kudos).toSet()
        }
    }
}
