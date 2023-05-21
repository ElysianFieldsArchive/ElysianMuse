package org.darkSolace.muse.story.service

import org.darkSolace.muse.story.model.StoryTag
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.repository.StoryTagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StoryTagService(
    @Autowired val storyRepository: StoryRepository,
    @Autowired val storyTagRepository: StoryTagRepository,
) {
    fun createStoryTag(tag: StoryTag): StoryTag {
        return storyTagRepository.save(tag)
    }

    /**
     * TODO: Controller methods and unittests - consult with Bibi
     */
    fun deleteStoryTag(tag: StoryTag): Boolean {
        val inUse = storyRepository.existsByStoryTagsContaining(tag)
        if (!inUse) storyTagRepository.save(tag)

        return !inUse
    }

    /**
     * TODO: Controller methods and unittests - consult with Bibi
     */
    fun replaceStoryTag(oldTag: StoryTag, newTag: StoryTag) {
        val storiesContainingTag = storyRepository.findAllByStoryTagsContaining(oldTag)
        if (newTag.id == null && !storyTagRepository.existsByName(newTag.name)) {
            //new tag doesn't exist yet -> creating it
            storyTagRepository.save(newTag)
        }

        storiesContainingTag.forEach { story ->
            story.storyTags.remove(oldTag)
            story.storyTags.add(newTag)
            storyRepository.save(story)
        }
    }
}
