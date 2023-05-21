package org.darkSolace.muse.story.controller

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.darkSolace.muse.story.model.dto.FilterStoriesDTO
import org.darkSolace.muse.story.model.dto.StoryChapterContributorDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.service.StoryContributionService
import org.darkSolace.muse.story.service.StoryService
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/story")
class StoryController(
    @Autowired private val storyService: StoryService,
    @Autowired private val storyContributionService: StoryContributionService,
) {
    @GetMapping("/{id}")
    @Transactional
    fun getStoryById(@PathVariable id: Long): ResponseEntity<*> {
        val story = storyService.getStoryById(id)
        return if (story != null) {
            ResponseEntity(StoryDTO.from(story), HttpStatus.OK)
        } else {
            ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/all")
    fun getAllStories(): Collection<StoryDTO> {
        return StoryDTO.fromCollection(storyService.getAllStories())
    }

    @PostMapping("/filtered")
    fun getStoriesFiltered(
        @RequestBody filters: FilterStoriesDTO,
    ): Collection<StoryDTO> {
        val stories = storyService.getStoriesFiltered(filters.ratings, filters.tags, filters.authors)
        return StoryDTO.fromCollection(stories)
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun createStory(@RequestBody @Valid story: StoryDTO): ResponseEntity<Unit> {
        val success = storyService.createStory(story)
        return if (success) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun editStory(
        @RequestBody @Valid editedStory: StoryDTO, authentication: Authentication?,
    ): ResponseEntity<Unit> {
        val story = storyService.getStoryById(editedStory.id ?: -1)
        val user = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user

        if (story == null || user == null) return ResponseEntity(HttpStatus.BAD_REQUEST)

        return if (user.role in listOf(Role.ADMINISTRATOR, Role.MODERATOR) || user in story.author) {
            val success = storyService.editStory(editedStory)
            if (success) ResponseEntity(HttpStatus.OK) else ResponseEntity(HttpStatus.BAD_REQUEST)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun deleteStory(
        @PathVariable id: Long, authentication: Authentication?,
    ): ResponseEntity<Unit> {
        val story = storyService.getStoryById(id)
        val user = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user

        if (story == null || user == null) return ResponseEntity(HttpStatus.BAD_REQUEST)

        return if (user.role in listOf(Role.ADMINISTRATOR, Role.MODERATOR) || user in story.author) {
            val success = storyService.deleteStory(id)
            if (success) ResponseEntity(HttpStatus.OK) else ResponseEntity(HttpStatus.BAD_REQUEST)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @PutMapping("/{storyId}/contributor")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addContributorToStory(
        @PathVariable storyId: Long,
        @RequestBody storyContributorDTO: StoryChapterContributorDTO,
        authentication: Authentication?,
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId) ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)

        return if (story.author.contains(requestUser) || requestUser?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )
        ) {
            val success = storyContributionService.addContributorToStory(
                storyId, storyContributorDTO.user.id ?: -1, storyContributorDTO.userTag
            )
            if (success) ResponseEntity(HttpStatus.OK)
            else ResponseEntity(HttpStatus.BAD_REQUEST)
        } else ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
    }

    @DeleteMapping("/{storyId}/contributor")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun removeContributorFromStory(
        @PathVariable storyId: Long,
        @RequestBody storyContributorDTO: StoryChapterContributorDTO,
        authentication: Authentication?,
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId) ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)

        return if (story.author.contains(requestUser) || requestUser?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )
        ) {
            val success = storyContributionService.removeContributorFromStory(
                storyId, storyContributorDTO.user.id ?: -1, storyContributorDTO.userTag
            )
            if (success) ResponseEntity(HttpStatus.OK)
            else ResponseEntity(HttpStatus.BAD_REQUEST)
        } else ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/{user}/contributions")
    fun getUserContributions(@PathVariable user: User?): ResponseEntity<*> {
        if (user == null) return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)

        return ResponseEntity(storyContributionService.getUserContributions(user), HttpStatus.OK)
    }
}
