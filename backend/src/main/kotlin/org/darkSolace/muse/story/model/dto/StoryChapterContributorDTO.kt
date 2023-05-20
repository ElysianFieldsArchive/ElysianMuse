package org.darkSolace.muse.story.model.dto

import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.model.dto.UserIdNameDTO

class StoryChapterContributorDTO(
    val user: UserIdNameDTO,
    val userTag: UserTag
)
