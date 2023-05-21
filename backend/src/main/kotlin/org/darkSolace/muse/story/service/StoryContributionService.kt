package org.darkSolace.muse.story.service

import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.model.dto.UserContributionDTO
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StoryContributionService(
    @Autowired private val storyRepository: StoryRepository,
    @Autowired private val userService: UserService,
    @Autowired private val storyService: StoryService,
) {
    fun addContributorToStory(storyId: Long, userId: Long, userTag: UserTag): Boolean {
        val story = storyRepository.findByIdOrNull(storyId)
        val user = userService.getById(userId)

        if (story == null || user == null) return false

        var change = false
        when (userTag) {
            UserTag.AUTHOR -> {
                story.author.add(user)
                change = true
            }

            UserTag.ARTIST -> {
                story.artist.add(user)
                change = true
            }

            UserTag.BETA -> {
                story.beta.add(user)
                change = true
            }

            else -> {
                //do nothing
            }
        }

        if (change) {
            storyRepository.save(story)
        }
        return true
    }

    fun removeContributorFromStory(storyId: Long, userId: Long, userTag: UserTag): Boolean {
        val story = storyRepository.findByIdOrNull(storyId)
        val user = userService.getById(userId)

        if (story == null || user == null) return false

        var change = false
        when (userTag) {
            UserTag.AUTHOR -> {
                story.author.remove(user)
                change = true
            }

            UserTag.ARTIST -> {
                story.artist.remove(user)
                change = true
            }

            UserTag.BETA -> {
                story.beta.remove(user)
                change = true
            }

            else -> {
                //do nothing
            }
        }
        if (change) {
            storyRepository.save(story)
        }
        return true
    }

    fun getUserContributions(user: User): UserContributionDTO {
        if (user.userSettings.hideContributions) return UserContributionDTO(emptyList(), emptyList())

        val stories = storyService.getAllStories()

        val storyBeta = stories.filter { it.beta.contains(user) }
        val storyAuthor = stories.filter { it.author.contains(user) }
        val storyArtist = stories.filter { it.artist.contains(user) }

        val chapters = stories.flatMap { it.chapters }

        val chapterBeta = chapters.filter { it.beta.contains(user) }
        val chapterArtist = chapters.filter { it.artist.contains(user) }

        return UserContributionDTO(
            StoryDTO.fromCollection((storyArtist + storyAuthor + storyBeta).distinct()),
            ChapterDTO.fromCollection((chapterArtist + chapterBeta).distinct())
        )
    }
}
