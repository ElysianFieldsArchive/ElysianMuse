package org.darkSolace.muse.story.controller

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.darkSolace.muse.story.model.dto.*
import org.darkSolace.muse.story.service.StoryService
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/story")
class StoryController(@Autowired private val storyService: StoryService) {
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
        @RequestBody filters: FilterStoriesDTO
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
        @RequestBody @Valid editedStory: StoryDTO, authentication: Authentication?
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
        @PathVariable id: Long, authentication: Authentication?
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

    @PutMapping("/{storyId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addChapter(
        @PathVariable storyId: Long,
        @RequestBody @Valid chapter: ChapterDTO,
        authentication: Authentication?,
    ): ResponseEntity<*> {
        var errorResponse: ResponseEntity<String>? = null
        val story = storyService.getStoryById(storyId)
        val user = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user

        if (story == null) errorResponse = ResponseEntity("Story not found", HttpStatus.BAD_REQUEST)
        if (story?.id != chapter.storyId && errorResponse == null) errorResponse =
            ResponseEntity("Chapter does not match story", HttpStatus.BAD_REQUEST)
        if (story?.author?.contains(user) == false && errorResponse == null) {
            errorResponse = ResponseEntity("User is not an author", HttpStatus.UNAUTHORIZED)
        }

        return if (errorResponse == null) {
            val success = storyService.addChapter(chapter)
            if (success) ResponseEntity<Unit>(HttpStatus.OK)
            else ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        } else errorResponse
    }

    @PostMapping("/{storyId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun editChapter(
        @PathVariable storyId: Long, @RequestBody @Valid editedChapter: ChapterDTO, authentication: Authentication?,

        ): ResponseEntity<*> {
        var errorResponse: ResponseEntity<String>? = null
        val story = storyService.getStoryById(storyId)
        val user = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user

        if (story == null) errorResponse = ResponseEntity("Story not found", HttpStatus.BAD_REQUEST)
        if (story?.id != editedChapter.storyId) errorResponse =
            ResponseEntity("Chapter does not match story", HttpStatus.BAD_REQUEST)
        if (story?.chapters?.map { it.id }?.contains(editedChapter.id) == false && errorResponse == null) {
            errorResponse = ResponseEntity("Chapter not in Story", HttpStatus.BAD_REQUEST)
        }
        if ((user?.role !in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            ) && story?.author?.contains(user) == false) && errorResponse == null
        ) {
            errorResponse = ResponseEntity("User is not an author", HttpStatus.UNAUTHORIZED)
        }

        return if (errorResponse == null) {
            val success = storyService.editChapter(editedChapter)
            if (success) ResponseEntity<Unit>(HttpStatus.OK)
            else ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        } else errorResponse
    }

    @DeleteMapping("/{storyId}/{chapterId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun deleteChapter(
        @PathVariable storyId: Long, @PathVariable chapterId: Long, authentication: Authentication?
    ): ResponseEntity<*> {
        val user = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId)

        return if (story?.chapters?.map { chapter -> chapter.id }
                ?.contains(chapterId) == true && (story.author.contains(user) || user?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            ))) {
            // user is author or mod/admin
            val success = storyService.deleteChapter(chapterId)
            if (success) ResponseEntity<Unit>(HttpStatus.OK)
            else ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        } else ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    @PutMapping("/{storyId}/contributor")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addContributorToStory(
        @PathVariable storyId: Long,
        @RequestBody storyContributorDTO: StoryChapterContributorDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId) ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)

        return if (story.author.contains(requestUser) || requestUser?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )
        ) {
            val success = storyService.addContributorToStory(
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
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId) ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)

        return if (story.author.contains(requestUser) || requestUser?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )
        ) {
            val success = storyService.removeContributorFromStory(
                storyId, storyContributorDTO.user.id ?: -1, storyContributorDTO.userTag
            )
            if (success) ResponseEntity(HttpStatus.OK)
            else ResponseEntity(HttpStatus.BAD_REQUEST)
        } else ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
    }

    @PutMapping("/{storyId}/{chapterId}/contributor")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addContributorToChapter(
        @PathVariable storyId: Long,
        @PathVariable chapterId: Long,
        @RequestBody chapterContributorDTO: StoryChapterContributorDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId)

        return when {
            story == null -> ResponseEntity(HttpStatus.BAD_REQUEST)
            !story.chapters.map { it.id }.contains(chapterId) -> {
                ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            (story.author.contains(requestUser) || requestUser?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )) -> {
                val success = storyService.addContributorToChapter(
                    storyId, chapterId, chapterContributorDTO.user.id ?: -1, chapterContributorDTO.userTag
                )
                if (success) ResponseEntity(HttpStatus.OK)
                else ResponseEntity(HttpStatus.BAD_REQUEST)

            }

            else -> ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
        }
    }

    @DeleteMapping("/{storyId}/{chapterId}/contributor")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun removeContributorFromChapter(
        @PathVariable storyId: Long,
        @PathVariable chapterId: Long,
        @RequestBody chapterContributorDTO: StoryChapterContributorDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId)

        return when {
            story == null -> ResponseEntity(HttpStatus.BAD_REQUEST)
            !story.chapters.map { it.id }.contains(chapterId) -> {
                ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            (story.author.contains(requestUser) || requestUser?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )) -> {
                val success = storyService.removeContributorFromChapter(
                    storyId, chapterId, chapterContributorDTO.user.id ?: -1, chapterContributorDTO.userTag
                )
                if (success) ResponseEntity(HttpStatus.OK)
                else ResponseEntity(HttpStatus.BAD_REQUEST)

            }

            else -> ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
        }
    }

    @PutMapping("/chapter/{chapterId}/comment")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addChapterComment(
        @PathVariable chapterId: Long, @RequestBody @Valid comment: ChapterCommentDTO, authentication: Authentication?
    ): ResponseEntity<*> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        if (requestUser?.id != comment.author?.id || requestUser?.id == null) {
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
        }

        val success = storyService.addChapterComment(chapterId, comment)
        return if (success) {
            ResponseEntity<Unit>(HttpStatus.OK)
        } else {
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/chapter/{chapterId}/comment")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun editChapterComment(
        @PathVariable chapterId: Long,
        @RequestBody @Valid editedComment: ChapterCommentDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        if ((requestUser?.id != editedComment.author?.id || requestUser?.id == null) && requestUser?.role !in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )
        ) {
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
        }

        val success = storyService.editChapterComment(editedComment)
        return if (success) {
            ResponseEntity<Unit>(HttpStatus.OK)
        } else {
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/chapter/{chapterId}/comment/{commentId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    @Transactional
    fun deleteChapterComment(
        @PathVariable chapterId: Long, @PathVariable commentId: Long, authentication: Authentication?
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val chapter = storyService.getChapterById(chapterId) ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        val comment =
            chapter.comments.firstOrNull { it.id == commentId } ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        if ((requestUser?.id != comment.author.id || requestUser?.id == null) && requestUser?.role !in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            )
        ) {
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
        }

        val success = storyService.deleteChapterComment(commentId)
        return if (success) {
            ResponseEntity<Unit>(HttpStatus.OK)
        } else {
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{user}/contributions")
    fun getUserContributions(@PathVariable user: User?): ResponseEntity<*> {
        if (user == null) return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)

        return ResponseEntity(storyService.getUserContributions(user), HttpStatus.OK)
    }
}