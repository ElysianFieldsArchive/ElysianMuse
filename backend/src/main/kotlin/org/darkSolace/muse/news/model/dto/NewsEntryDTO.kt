package org.darkSolace.muse.news.model.dto

import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class NewsEntryDTO {
    var id: Long? = null
    var author: UserIdNameDTO? = null
    var subject: String = ""
    var content: String = ""
    var creationDate: Date = Date()
    var newsComments = emptyList<NewsCommentDTO>()

    companion object {
        fun fromList(news: List<NewsEntry>) = news.map { from(it) }
        fun from(news: NewsEntry) = NewsEntryDTO().apply {
            id = news.id
            author = UserIdNameDTO.from(news.author)
            subject = news.subject
            content = news.content
            creationDate = news.creationDate
            newsComments = NewsCommentDTO.fromList(news.newsComments)
        }
    }
}
