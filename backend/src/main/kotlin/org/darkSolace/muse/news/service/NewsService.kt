package org.darkSolace.muse.news.service

import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.news.model.dto.NewsCommentDTO
import org.darkSolace.muse.news.model.dto.NewsEntryDTO
import org.darkSolace.muse.news.repository.NewsCommentRepository
import org.darkSolace.muse.news.repository.NewsRepository
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class NewsService {
    @Autowired
    lateinit var newsRepository: NewsRepository

    @Autowired
    lateinit var newsCommentRepository: NewsCommentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun getLast(size: Int): List<NewsEntry> = newsRepository.findByOrderByCreationDateDesc().take(size)

    fun addCommentToNews(id: Long, comment: NewsCommentDTO): Boolean {
        val author = userRepository.findById(comment.author?.id ?: -1).getOrNull() ?: return false
        val news = newsRepository.findById(id).getOrNull() ?: return false
        val newsComment = NewsComment().also {
            it.author = author
            it.content = comment.content
        }

        news.newsComments.add(newsComment)

        newsCommentRepository.save(newsComment)
        newsRepository.save(news)
        return true
    }

    fun createNews(newsDto: NewsEntryDTO): Boolean {
        val author = userRepository.findById(newsDto.author.id ?: -1).getOrNull() ?: return false
        val news = NewsEntry().also {
            it.author = author
            it.subject = newsDto.subject
            it.content = newsDto.content
        }

        newsRepository.save(news)
        return true
    }

    fun getNews(id: Long): NewsEntry? {
        return newsRepository.findByIdOrNull(id)
    }

    fun editNews(id: Long, newsDto: NewsEntryDTO): Boolean {
        val news = newsRepository.findByIdOrNull(id) ?: return false
        news.apply {
            subject = newsDto.subject
            content = newsDto.content
        }

        newsRepository.save(news)
        return true
    }

    fun editComment(commentId: Long, commentDto: NewsCommentDTO): Boolean {
        val comment = newsCommentRepository.findByIdOrNull(commentId) ?: return false
        comment.apply {
            content = commentDto.content
        }

        newsCommentRepository.save(comment)
        return true
    }

    fun getAllNews(): List<NewsEntry> {
        return newsRepository.findByOrderByCreationDateDesc()
    }

}