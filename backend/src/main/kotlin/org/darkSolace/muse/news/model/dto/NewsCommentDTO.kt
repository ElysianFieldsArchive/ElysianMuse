package org.darkSolace.muse.news.model.dto

import org.darkSolace.muse.DTO
import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class NewsCommentDTO {
    var id: Long? = null
    var author: UserIdNameDTO? = null
    var content: String = ""
    var creationDate: Date = Date()

    companion object : DTO<NewsComment, NewsCommentDTO> {
        override fun from(item: NewsComment) = NewsCommentDTO().apply {
            id = item.id
            author = UserIdNameDTO.from(item.author)
            content = item.content
            creationDate = item.creationDate
        }
    }
}
