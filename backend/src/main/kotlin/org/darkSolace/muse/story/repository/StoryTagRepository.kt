package org.darkSolace.muse.story.repository

import org.darkSolace.muse.story.model.StoryTag
import org.springframework.data.repository.CrudRepository

interface StoryTagRepository : CrudRepository<StoryTag, Long> {
    fun existsByName(name: String): Boolean
}