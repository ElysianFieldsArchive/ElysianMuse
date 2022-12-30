package org.darkSolace.muse.news.model.dto

import org.darkSolace.muse.news.model.NewsEntry

class   NewsEntryDto(newsEntry: NewsEntry) {
    var id = newsEntry.id

    //TODO: Change to user dto
    var author = newsEntry.author

    var subject = newsEntry.subject

    var content = newsEntry.content

    val creationDate = newsEntry.creationDate

    var newsComments = NewsCommentDto.fromList(newsEntry.newsComments)

    companion object {
        fun fromList(news: List<NewsEntry>) = news.map { NewsEntryDto(it) }
    }
}