package org.darkSolace.muse.news.repository

import org.darkSolace.muse.news.model.NewsComment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD Repository for the [NewsComment] entity.
 */
@Repository
interface NewsCommentRepository : CrudRepository<NewsComment, Long>
