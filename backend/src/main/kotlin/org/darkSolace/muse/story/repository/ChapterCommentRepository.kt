package org.darkSolace.muse.story.repository

import org.darkSolace.muse.story.model.ChapterComment
import org.springframework.data.repository.CrudRepository

interface ChapterCommentRepository : CrudRepository<ChapterComment, Long> {
    fun findByChapterId(chapterId: Long): List<ChapterComment>
}