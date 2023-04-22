package org.darkSolace.muse.story.model.dto

import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.story.model.Story

data class UserContributionDTO(val stories: List<Story>, val chapters: List<Chapter>)
