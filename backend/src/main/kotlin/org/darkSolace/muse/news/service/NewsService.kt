package org.darkSolace.muse.news.service

import jakarta.transaction.Transactional
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

/**
 * Service class for [NewsEntry] and [NewsComment] related tasks.
 */
@Service
class NewsService {
    @Autowired
    lateinit var newsRepository: NewsRepository

    @Autowired
    lateinit var newsCommentRepository: NewsCommentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    /**
     * Returns the last (newest) _amount_ [NewsEntry]s.
     *
     * @param amount number of entries to fetch
     * @return list of [NewsEntry]s
     */
    @Transactional
    fun getLast(amount: Int): List<NewsEntry> = newsRepository.findByOrderByCreationDateDesc().take(amount)

    /**
     * Adds a new [NewsComment] to an existing [NewsEntry].
     *
     * @param id Id of the [NewsEntry] to add the comment to
     * @param comment A [NewsCommentDTO] containing the details of the comment
     *
     * @return _true_ if the comment was added successfully, _false_ in case of an error
     */
    fun addCommentToNews(id: Long, comment: NewsCommentDTO): Boolean {
        val author = userRepository.findById(comment.author?.id ?: -1).getOrNull() ?: return false
        val news = newsRepository.findById(id).getOrNull() ?: return false
        if (comment.author == null) return false

        val newsComment = NewsComment().also {
            it.author = author
            it.content = comment.content
        }

        news.newsComments.add(newsComment)

        newsCommentRepository.save(newsComment)
        newsRepository.save(news)
        return true
    }

    /**
     * Creates a new [NewsEntry].
     *
     * @param newsDto a [NewsEntryDTO] containing all the details of the [NewsEntry] to create
     *
     * @return _true_ if the [NewsEntry] was created successfully, _false_ in case of an invalid [NewsEntryDTO].
     */
    fun createNews(newsDto: NewsEntryDTO): Boolean {
        val author = userRepository.findById(newsDto.author?.id ?: -1).getOrNull() ?: return false
        val news = NewsEntry().also {
            it.author = author
            it.subject = newsDto.subject
            it.content = newsDto.content
        }

        newsRepository.save(news)
        return true
    }

    /**
     * Returns a [NewsEntry] identified by its id.
     *
     * @param id the id of the [NewsEntry] to retrieve
     * @return the loaded [NewsEntry] or _null_ if no [NewsEntry] with the specified id exists
     */
    fun getNews(id: Long): NewsEntry? {
        return newsRepository.findByIdOrNull(id)
    }

    /**
     * Edits an existing [NewsEntry], new values are specified by an [NewsEntryDTO]
     *
     * @param id the id of the [NewsEntry] to be edited
     * @param newsDto [NewsEntryDTO] containing the edited details
     *
     * @return _true_ if the [NewsEntry] was edited successfully, _false_ in case of an error
     */
    fun editNews(id: Long, newsDto: NewsEntryDTO): Boolean {
        val news = newsRepository.findByIdOrNull(id) ?: return false
        val author = userRepository.findById(newsDto.author?.id ?: -1).getOrNull()
        if (newsDto.subject.isBlank() || newsDto.content.isBlank() || author == null) return false
        news.apply {
            subject = newsDto.subject
            content = newsDto.content
            this.author = author
        }

        newsRepository.save(news)
        return true
    }

    /**
     * Edits an existing [NewsComment], new values are specified by an [NewsCommentDTO]
     *
     * @param commentId the id of the [NewsComment] to be edited
     * @param commentDto [NewsCommentDTO] containing the edited details
     *
     * @return _true_ if the [NewsComment] was edited successfully, _false_ in case of an error
     */
    fun editComment(commentId: Long, commentDto: NewsCommentDTO): Boolean {
        val comment = newsCommentRepository.findByIdOrNull(commentId) ?: return false
        val author = userRepository.findById(commentDto.author?.id ?: -1).getOrNull() ?: return false
        //check if edit author is original comment author
        if (comment.author.id != author.id) return false

        comment.apply {
            this.author = author
            content = commentDto.content
        }

        newsCommentRepository.save(comment)
        return true
    }

    /**
     * Returns all [NewsEntry]s
     *
     * @return list of [NewsEntry]s, might be empty if no news exist
     */
    @Transactional
    fun getAllNews(): List<NewsEntry> {
        return newsRepository.findByOrderByCreationDateDesc()
    }

    /**
     * Removes a persisted [NewsEntry]
     *
     * @param id of the [NewsEntry] to be deleted
     * @return true if deletion was successful, false in case of error
     */
    @Transactional
    fun deleteNews(id: Long): Boolean = if (newsRepository.existsById(id)) {
        newsRepository.deleteById(id)
        true
    } else {
        false
    }

    /**
     * Removes a persisted [NewsComment]
     *
     * @param id of the [NewsComment] to be deleted
     * @return true if deleteion was successful, false in case of error
     */
    @Transactional
    fun deleteComment(id: Long): Boolean {
        //remove comment from news
        val comment = newsCommentRepository.findById(id).getOrNull() ?: return false
        return try {
            val news = newsRepository.findByNewsCommentsContains(comment)
            news.newsComments.removeIf { it.id == comment.id }
            newsRepository.save(news)

            //remove news
            newsCommentRepository.deleteById(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}
