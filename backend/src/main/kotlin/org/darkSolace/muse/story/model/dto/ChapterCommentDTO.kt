package org.darkSolace.muse.story.model.dto

import org.darkSolace.muse.story.model.ChapterComment
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class ChapterCommentDTO {
    var id: Long? = null
    var authorApproved: Boolean = false
    var author: UserIdNameDTO? = null
    var chapterId: Long? = null
    var content: String = ""
    var publishedDate: Date = Date()
    var referenceComment: Long? = null

    companion object {
        fun fromCollection(list: Collection<ChapterComment>) = list.map { from(it) }
        fun from(chapterComment: ChapterComment): ChapterCommentDTO =
            ChapterCommentDTO().apply {
                this.id = chapterComment.id
                this.authorApproved = chapterComment.authorApproved
                this.author = UserIdNameDTO.from(chapterComment.author)
                this.chapterId = chapterComment.chapterId
                this.content = chapterComment.content
                this.publishedDate = chapterComment.publishedDate
                this.referenceComment = chapterComment.referenceComment
            }
    }
}
