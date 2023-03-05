package org.darkSolace.muse.story.repository

import org.darkSolace.muse.story.model.Story
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryRepository: CrudRepository<Story, Long> {
}