package org.darkSolace.muse.story.service

import jakarta.transaction.Transactional
import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.story.model.ChapterComment
import org.darkSolace.muse.story.model.Rating
import org.darkSolace.muse.story.model.Story
import org.darkSolace.muse.story.model.StoryTag
import org.darkSolace.muse.story.model.dto.ChapterCommentDTO
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.model.dto.UserContributionDTO
import org.darkSolace.muse.story.repository.ChapterCommentRepository
import org.darkSolace.muse.story.repository.ChapterRepository
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.repository.StoryTagRepository
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class StoryService(
    @Autowired private val storyRepository: StoryRepository,
    @Autowired private val chapterRepository: ChapterRepository,
    @Autowired private val chapterCommentRepository: ChapterCommentRepository,
    @Autowired private val storyTagRepository: StoryTagRepository,
    @Autowired private val userService: UserService,
) {
    fun getStoryById(id: Long): Story? = storyRepository.findByIdOrNull(id)
    fun getChapterById(chapterId: Long): Chapter? = chapterRepository.findByIdOrNull(chapterId)

    @Transactional
    fun getAllStories(): List<Story> = storyRepository.findAll().toList()

    @Transactional
    fun getStoriesFiltered(
        ratings: List<Rating>? = null, tags: List<StoryTag>? = null, authors: List<User>? = null
    ): List<Story> {
        var stories = storyRepository.findAll()

        if (ratings?.isNotEmpty() == true) {
            stories = stories.filter { story -> ratings.any { rating -> story.rating == rating } }
        }

        if (tags?.isNotEmpty() == true) {
            stories = stories.filter { story -> tags.all { tag -> story.storyTags.contains(tag) } }
        }

        if (!authors.isNullOrEmpty()) {
            stories = stories.filter { story -> authors.any { user -> story.author.contains(user) } }
        }

        return stories.toList()
    }

    @Transactional
    fun createStory(story: StoryDTO): Boolean {
        val (authors, artists, betas) = story.resolveUsersArtistsBetas()

        //only authors are required,
        if (authors.isEmpty()) return false

        var newStory = Story().updateStory(story, authors, emptyList(), artists, betas)

        //save tags if there are any first
        newStory.storyTags.forEach { storyTagRepository.save(it) }

        newStory = storyRepository.save(newStory)

        //save chapters if there are any first
        story.chapters.forEach { chapterDTO ->
            val chapter = Chapter().also {
                it.title = chapterDTO.title
                it.summary = chapterDTO.summary
                it.startNotes = chapterDTO.startNotes
                it.endNotes = chapterDTO.endNotes
                it.content = chapterDTO.content
                it.publishedDate = Date()
                it.updatedDate = Date()
                it.beta = chapterDTO.beta.mapNotNull { userService.getById(it.id ?: -1) }.toMutableSet()
                it.artist = chapterDTO.artist.mapNotNull { userService.getById(it.id ?: -1) }.toMutableSet()
                it.storyBanner = chapterDTO.storyBanner
                it.storyId = newStory.id
            }
            chapterRepository.save(chapter)
            newStory.chapters.add(chapter)
        }
        newStory = storyRepository.save(newStory)

        return true
    }

    @Transactional
    fun editStory(story: StoryDTO): Boolean {
        val (authors, artists, betas) = story.resolveUsersArtistsBetas()
        val chapters = story.resolveChapters()

        val existingStory = storyRepository.findByIdOrNull(story.id ?: -1)

        //authors are required and story must exist
        if (authors.isEmpty() || existingStory == null) return false

        existingStory.updateStory(story, authors, chapters, artists, betas)

        //save chapters in case there are changes as well?
        existingStory.chapters.forEach { chapterRepository.save(it) }
        storyRepository.save(existingStory)

        return true
    }

    @Transactional
    fun deleteStory(id: Long): Boolean {
        val story = storyRepository.findByIdOrNull(id) ?: return false

        //remove all chapters
        story.chapters.forEach { chapter ->
            //remove all comments from chapter
            val comments = chapterCommentRepository.findByChapterId(chapter.id ?: -1)
            comments.forEach { comment ->
                chapterCommentRepository.delete(comment)
            }

            chapterRepository.delete(chapter)
        }
        storyRepository.delete(story)

        return true
    }

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
        val chapter = chapterRepository.findByIdOrNull(id) ?: return false
        val story = storyRepository.findByIdOrNull(chapter.storyId ?: -1) ?: return false

        story.chapters.remove(chapter)
        storyRepository.save(story)

        chapterRepository.delete(chapter)
        return true
    }

    fun addContributorToStory(storyId: Long, userId: Long, userTag: UserTag): Boolean {
        val story = storyRepository.findByIdOrNull(storyId) ?: return false
        val user = userService.getById(userId) ?: return false

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
        val story = storyRepository.findByIdOrNull(storyId) ?: return false
        val user = userService.getById(userId) ?: return false

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

    fun addContributorToChapter(storyId: Long, chapterId: Long, userId: Long, userTag: UserTag): Boolean {
        val story = storyRepository.findByIdOrNull(storyId) ?: return false
        val chapter = story.chapters.first { it.id == chapterId }
        val user = userService.getById(userId) ?: return false

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
        val story = storyRepository.findByIdOrNull(storyId) ?: return false
        val chapter = story.chapters.first { it.id == chapterId }
        val user = userService.getById(userId) ?: return false

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

    @Transactional
    fun addChapterComment(chapterId: Long, comment: ChapterCommentDTO): Boolean {
        val chapter = chapterRepository.findByIdOrNull(chapterId) ?: return false
        val commenter = userService.getById(comment.author?.id ?: return false) ?: return false
        val story = storyRepository.findByIdOrNull(chapter.storyId ?: -1) ?: return false

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
        val chapter = chapterRepository.findByIdOrNull(editedChapterComment.chapterId ?: -1) ?: return false
        val story = storyRepository.findByIdOrNull(chapter.storyId ?: -1) ?: return false
        val commenter = userService.getById(editedChapterComment.author?.id ?: return false) ?: return false
        var comment = chapterCommentRepository.findByIdOrNull(editedChapterComment.id ?: -1) ?: return false

        comment = comment.apply {
            this.authorApproved = !story.commentModeration
            this.author = commenter
            this.chapterId = chapter.id
            this.content = editedChapterComment.content
            this.publishedDate = editedChapterComment.publishedDate
            this.referenceComment = editedChapterComment.referenceComment
        }

        chapterCommentRepository.save(comment)
        return true
    }


    @Transactional
    fun deleteChapterComment(chapterCommentId: Long): Boolean {
        val chapterComment = chapterCommentRepository.findByIdOrNull(chapterCommentId) ?: return false
        val chapter = chapterRepository.findByIdOrNull(chapterComment.chapterId ?: -1) ?: return false

        chapter.comments.remove(chapterComment)
        chapterRepository.save(chapter)
        return true
    }


    fun getUserContributions(user: User): UserContributionDTO {
        if (user.userSettings.hideContributions) return UserContributionDTO(emptyList(), emptyList())

        val stories = getAllStories()

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

    private fun generateEpub(id: Long) {
        TODO("research epub creation")
    }

    private fun generatePdf(id: Long) {
        TODO("research pdf creation")
    }

    private fun Story.updateStory(
        changes: StoryDTO,
        authors: List<User>,
        chapters: List<Chapter>,
        artists: List<User> = emptyList(),
        betas: List<User> = emptyList()
    ): Story {
        this.id = changes.id
        this.title = changes.title
        this.summary = changes.summary
        this.storyNotes = changes.storyNotes
        this.goalChapterCount = changes.goalChapterCount
        this.completed = changes.completed
        this.commentModeration = changes.commentModeration
        this.eventId = changes.eventId
        this.author.addAll(authors)
        this.beta.addAll(betas)
        this.artist.addAll(artists)
        this.publishedDate = changes.publishedDate
        this.rating = changes.rating
        this.storyBanner = changes.storyBanner
        this.chapters.addAll(chapters)
        this.storyTags.addAll(changes.storyTags)

        return this
    }

    private fun StoryDTO.resolveUsersArtistsBetas(): Array<List<User>> {
        val authors = userService.getByIds(this.author.map { it.id }.filterNotNull())
        val artists = userService.getByIds(this.artist.map { it.id }.filterNotNull())
        val betas = userService.getByIds(this.beta.map { it.id }.filterNotNull())

        return arrayOf(authors, artists, betas)
    }

    private fun ChapterDTO.resolveArtistsBetas(): Array<List<User>> {
        val artists = userService.getByIds(this.artist.map { it.id }.filterNotNull())
        val betas = userService.getByIds(this.beta.map { it.id }.filterNotNull())

        return arrayOf(artists, betas)
    }

    private fun StoryDTO.resolveChapters(): List<Chapter> =
        chapterRepository.findAllById(this.chapters.mapNotNull { it.id }).toList()
}
