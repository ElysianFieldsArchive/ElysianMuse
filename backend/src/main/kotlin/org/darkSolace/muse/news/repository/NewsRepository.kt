package org.darkSolace.muse.news.repository

import org.darkSolace.muse.news.model.NewsEntry
import org.springframework.data.repository.CrudRepository

interface NewsRepository : CrudRepository<NewsEntry, Long> {
    fun findByOrderByCreationDateDesc(): List<NewsEntry>
}