package org.darkSolace.muse.news.repository

import org.darkSolace.muse.news.model.NewsComment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsCommentRepository : CrudRepository<NewsComment, Long>
