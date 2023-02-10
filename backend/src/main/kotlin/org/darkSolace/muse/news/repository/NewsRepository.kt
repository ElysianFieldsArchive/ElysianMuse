package org.darkSolace.muse.news.repository

import org.darkSolace.muse.news.model.NewsEntry
import org.springframework.data.repository.CrudRepository

/**
 * CRUD Repository for the [NewsEntry] entity.
 */
interface NewsRepository : CrudRepository<NewsEntry, Long> {
    /**
     * Retrieves a list of [NewsEntry]s
     *
     * @return a list of [NewsEntry], might be empty
     */
    fun findByOrderByCreationDateDesc(): List<NewsEntry>
}
