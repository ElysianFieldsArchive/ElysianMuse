package org.darkSolace.muse.news.service

import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.news.model.dto.NewsCommentDto
import org.darkSolace.muse.news.model.dto.NewsEntryDto
import org.darkSolace.muse.news.repository.NewsCommentRepository
import org.darkSolace.muse.news.repository.NewsRepository
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class NewsService {
    @Autowired
    lateinit var newsRepository: NewsRepository

    @Autowired
    lateinit var newsCommentRepository: NewsCommentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun getLast(size: Int): List<NewsEntry> = newsRepository.findTopByOrderByCreationDateDesc(size)

    fun addCommentToNews(id: Long, comment: NewsCommentDto): Boolean {
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

    fun createNews(newsDto: NewsEntryDto): Boolean {
        val author = userRepository.findById(newsDto.author?.id ?: -1).getOrNull() ?: return false
        val news = NewsEntry().also {
            it.author = author
            it.subject = newsDto.subject
            it.content = newsDto.content
        }

        newsRepository.save(news)
        return true
    }

}