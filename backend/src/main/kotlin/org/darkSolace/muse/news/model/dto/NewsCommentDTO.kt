package org.darkSolace.muse.news.model.dto

import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.user.model.User
import java.util.*

class NewsCommentDTO {
    var id: Long? = null
    var author: User? = null
    var content: String = ""
    var creationDate: Date = Date()

    companion object {
        fun fromList(comments: List<NewsComment>) = comments.map { from(it) }
        fun from(comment: NewsComment) = NewsCommentDTO().apply {
            id = comment.id
            author = comment.author
            content = comment.content
            creationDate = comment.creationDate
        }
    }
}
