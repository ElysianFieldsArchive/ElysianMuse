package org.darkSolace.muse.news.model.dto

import org.darkSolace.muse.DTO
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

    companion object : DTO<NewsEntry, NewsEntryDTO> {
        override fun from(item: NewsEntry) = NewsEntryDTO().apply {
            id = item.id
            author = UserIdNameDTO.from(item.author)
            subject = item.subject
            content = item.content
            creationDate = item.creationDate
            newsComments = NewsCommentDTO.fromCollection(item.newsComments).toList()
        }
    }
}
