package org.darkSolace.muse.news.model.dto

import org.darkSolace.muse.news.model.NewsComment

class NewsCommentDto(comment: NewsComment) {
    val id = comment.id
    val author = comment.author
    val content = comment.content
    val creationDate = comment.creationDate

    companion object {
        fun fromList(comments: List<NewsComment>) = comments.map { NewsCommentDto(it) }
    }
}