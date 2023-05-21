package org.darkSolace.muse.story.controller

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.darkSolace.muse.story.model.dto.ChapterCommentDTO
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryChapterContributorDTO
import org.darkSolace.muse.story.service.StoryChapterService
import org.darkSolace.muse.story.service.StoryService
import org.darkSolace.muse.user.model.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/story")
class StoryChapterController(
    @Autowired private val storyService: StoryService,
    @Autowired private val chapterService: StoryChapterService,
) {
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
            val success = chapterService.addChapter(chapter)
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
            val success = chapterService.editChapter(editedChapter)
            if (success) ResponseEntity<Unit>(HttpStatus.OK)
            else ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        } else errorResponse
    }

    @DeleteMapping("/{storyId}/{chapterId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun deleteChapter(
        @PathVariable storyId: Long, @PathVariable chapterId: Long, authentication: Authentication?,
    ): ResponseEntity<*> {
        val user = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val story = storyService.getStoryById(storyId)

        return if (story?.chapters?.map { chapter -> chapter.id }
                ?.contains(chapterId) == true && (story.author.contains(user) || user?.role in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            ))) {
            // user is author or mod/admin
            val success = chapterService.deleteChapter(chapterId)
            if (success) ResponseEntity<Unit>(HttpStatus.OK)
            else ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        } else ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    @PutMapping("/{storyId}/{chapterId}/contributor")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addContributorToChapter(
        @PathVariable storyId: Long,
        @PathVariable chapterId: Long,
        @RequestBody chapterContributorDTO: StoryChapterContributorDTO,
        authentication: Authentication?,
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
                val success = chapterService.addContributorToChapter(
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
        authentication: Authentication?,
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
                val success = chapterService.removeContributorFromChapter(
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
        @PathVariable chapterId: Long, @RequestBody @Valid comment: ChapterCommentDTO, authentication: Authentication?,
    ): ResponseEntity<*> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        if (requestUser?.id != comment.author?.id || requestUser?.id == null) {
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
        }

        val success = chapterService.addChapterComment(chapterId, comment)
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
        authentication: Authentication?,
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        if (chapterId != editedComment.chapterId) return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)

        return when {
            (requestUser?.id != editedComment.author?.id || requestUser?.id == null) && requestUser?.role !in listOf(
                Role.ADMINISTRATOR, Role.MODERATOR
            ) -> {
                ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
            }

            else -> {
                val success = chapterService.editChapterComment(editedComment)
                if (success) {
                    ResponseEntity<Unit>(HttpStatus.OK)
                } else {
                    ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
                }
            }
        }
    }

    @DeleteMapping("/chapter/{chapterId}/comment/{commentId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    @Transactional
    fun deleteChapterComment(
        @PathVariable chapterId: Long, @PathVariable commentId: Long, authentication: Authentication?,
    ): ResponseEntity<Unit> {
        val requestUser = (authentication?.principal as org.darkSolace.muse.security.model.UserDetails?)?.user
        val chapter = chapterService.getChapterById(chapterId)
        val comment = chapter?.comments?.firstOrNull { it.id == commentId }

        return when {
            chapter == null || comment == null -> {
                ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
            }

            (requestUser?.id != comment.author.id || requestUser?.id == null) && requestUser?.role !in listOf(
                Role.ADMINISTRATOR,
                Role.MODERATOR
            ) -> {
                ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
            }

            else -> {
                val success = chapterService.deleteChapterComment(commentId)

                if (success) {
                    ResponseEntity<Unit>(HttpStatus.OK)
                } else {
                    ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
                }
            }
        }
    }
}
