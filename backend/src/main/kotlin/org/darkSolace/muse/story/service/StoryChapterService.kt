package org.darkSolace.muse.story.service

import jakarta.transaction.Transactional
import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.story.model.ChapterComment
import org.darkSolace.muse.story.model.dto.ChapterCommentDTO
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.repository.ChapterCommentRepository
import org.darkSolace.muse.story.repository.ChapterRepository
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StoryChapterService(
    @Autowired private val storyRepository: StoryRepository,
    @Autowired private val chapterRepository: ChapterRepository,
    @Autowired private val chapterCommentRepository: ChapterCommentRepository,
    @Autowired private val userService: UserService,
) {
    fun getChapterById(chapterId: Long): Chapter? = chapterRepository.findByIdOrNull(chapterId)

    @Transactional
    fun addChapter(newChapter: ChapterDTO): Boolean {
        val story = storyRepository.findByIdOrNull(newChapter.storyId ?: -1) ?: return false

        val (artists, betas) = newChapter.resolveArtistsBetas()

        val chapter = Chapter().also {
            it.id = newChapter.id
            it.title = newChapter.title
            it.summary = newChapter.summary
            it.startNotes = newChapter.startNotes
            it.endNotes = newChapter.endNotes
            it.content = newChapter.content
            it.publishedDate = newChapter.publishedDate
            it.artist = artists.toMutableSet()
            it.beta = betas.toMutableSet()
            it.storyBanner = newChapter.storyBanner
            it.storyId = story.id
        }

        story.chapters.add(chapter)
        chapterRepository.save(chapter)
        storyRepository.save(story)

        return true
    }

    @Transactional
    fun editChapter(chapter: ChapterDTO): Boolean {
        var oldChapter = chapterRepository.findByIdOrNull(chapter.id ?: -1) ?: return false
        val (artists, betas) = chapter.resolveArtistsBetas()

        oldChapter = oldChapter.also {
            it.id = chapter.id
            it.title = chapter.title
            it.summary = chapter.summary
            it.startNotes = chapter.startNotes
            it.endNotes = chapter.endNotes
            it.content = chapter.content
            it.publishedDate = chapter.publishedDate
            it.artist = artists.toMutableSet()
            it.beta = betas.toMutableSet()
            it.storyBanner = chapter.storyBanner
        }

        chapterRepository.save(oldChapter)
        return true
    }

    fun deleteChapter(id: Long): Boolean {
        val chapter = chapterRepository.findByIdOrNull(id)
        val story = storyRepository.findByIdOrNull(chapter?.storyId ?: -1)

        if (story == null || chapter == null) return false

        story.chapters.remove(chapter)
        storyRepository.save(story)

        chapterRepository.delete(chapter)
        return true
    }

    @Transactional
    fun addChapterComment(chapterId: Long, comment: ChapterCommentDTO): Boolean {
        val chapter = chapterRepository.findByIdOrNull(chapterId)
        val commenter = userService.getById(comment.author?.id ?: -1)
        val story = storyRepository.findByIdOrNull(chapter?.storyId ?: -1)

        if (commenter == null || story == null || chapter == null) return false

        val chapterComment = ChapterComment().apply {
            this.authorApproved = !story.commentModeration
            this.author = commenter
            this.chapterId = chapter.id
            this.content = comment.content
            this.publishedDate = comment.publishedDate
            this.referenceComment = comment.referenceComment
        }

        chapterCommentRepository.save(chapterComment)
        chapter.comments.add(chapterComment)
        chapterRepository.save(chapter)

        return true
    }

    @Transactional
    fun editChapterComment(editedChapterComment: ChapterCommentDTO): Boolean {
        val chapter = chapterRepository.findByIdOrNull(editedChapterComment.chapterId ?: -1)
        val story = storyRepository.findByIdOrNull(chapter?.storyId ?: -1)
        val commenter = userService.getById(editedChapterComment.author?.id ?: -1)
        var comment = chapterCommentRepository.findByIdOrNull(editedChapterComment.id ?: -1)

        return when {
            comment == null -> false
            commenter == null -> false
            story == null -> false
            chapter == null -> false
            else -> {
                comment = comment.apply {
                    this.authorApproved = !story.commentModeration
                    this.author = commenter
                    this.chapterId = chapter.id
                    this.content = editedChapterComment.content
                    this.publishedDate = editedChapterComment.publishedDate
                    this.referenceComment = editedChapterComment.referenceComment
                }

                chapterCommentRepository.save(comment)
                true
            }
        }
    }


    @Transactional
    fun deleteChapterComment(chapterCommentId: Long): Boolean {
        val chapterComment = chapterCommentRepository.findByIdOrNull(chapterCommentId)
        val chapter = chapterRepository.findByIdOrNull(chapterComment?.chapterId ?: -1)

        if (chapterComment == null || chapter == null) return false

        chapter.comments.remove(chapterComment)
        chapterRepository.save(chapter)
        return true
    }

    fun addContributorToChapter(storyId: Long, chapterId: Long, userId: Long, userTag: UserTag): Boolean {
        val story = storyRepository.findByIdOrNull(storyId)
        val chapter = story?.chapters?.firstOrNull { it.id == chapterId }
        val user = userService.getById(userId)

        if (chapter == null || user == null) return false

        var change = false
        when (userTag) {
            UserTag.ARTIST -> {
                chapter.artist.add(user)
                change = true
            }

            UserTag.BETA -> {
                chapter.beta.add(user)
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

    fun removeContributorFromChapter(storyId: Long, chapterId: Long, userId: Long, userTag: UserTag): Boolean {
        val story = storyRepository.findByIdOrNull(storyId)
        val chapter = story?.chapters?.firstOrNull { it.id == chapterId }
        val user = userService.getById(userId)

        if (chapter == null || user == null) return false

        var change = false
        when (userTag) {
            UserTag.ARTIST -> {
                chapter.artist.remove(user)
                change = true
            }

            UserTag.BETA -> {
                chapter.beta.remove(user)
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

    private fun ChapterDTO.resolveArtistsBetas(): Array<List<User>> {
        val artists = userService.getByIds(this.artist.mapNotNull { it.id })
        val betas = userService.getByIds(this.beta.mapNotNull { it.id })

        return arrayOf(artists, betas)
    }
}
