package org.darkSolace.muse.news.model.dto

import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.user.model.dto.UserIdNameDTO

class NewsEntryDTO(newsEntry: NewsEntry) {
    var id = newsEntry.id

    var author = UserIdNameDTO(newsEntry.author)

    var subject = newsEntry.subject

    var content = newsEntry.content

    val creationDate = newsEntry.creationDate

    var newsComments = NewsCommentDTO.fromList(newsEntry.newsComments)

    companion object {
        fun fromList(news: List<NewsEntry>) = news.map { NewsEntryDTO(it) }
    }
}