package org.darkSolace.muse.story.model.dto

import org.darkSolace.muse.story.model.Rating
import org.darkSolace.muse.story.model.StoryTag
import org.darkSolace.muse.user.model.User

class FilterStoriesDTO(
    val ratings: List<Rating> = emptyList(),
    val tags: List<StoryTag> = emptyList(),
    val authors: List<User> = emptyList(),
)
