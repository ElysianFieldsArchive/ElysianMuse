package org.darkSolace.muse.story.repository

import org.darkSolace.muse.story.model.Chapter
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChapterRepository : CrudRepository<Chapter, Long> {
}