package org.darkSolace.muse.story.repository

import org.darkSolace.muse.story.model.Story
import org.darkSolace.muse.story.model.StoryTag
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryRepository : CrudRepository<Story, Long> {
    fun existsByStoryTagsContaining(tag: StoryTag): Boolean
    fun findAllByStoryTagsContaining(tag: StoryTag): List<Story>
}