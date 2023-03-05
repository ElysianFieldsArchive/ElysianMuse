package org.darkSolace.muse.story.service

import jakarta.transaction.Transactional
import org.darkSolace.muse.story.model.*
import org.darkSolace.muse.story.model.dto.ChapterCommentDTO
import org.darkSolace.muse.story.model.dto.ChapterDTO
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.repository.ChapterCommentRepository
import org.darkSolace.muse.story.repository.ChapterRepository
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StoryService(
    @Autowired private val storyRepository: StoryRepository,
    @Autowired private val chapterRepository: ChapterRepository,
    @Autowired private val chapterCommentRepository: ChapterCommentRepository,
    @Autowired private val userRepository: UserRepository
) {
    fun getStoryById(id: Long): Story? = storyRepository.findByIdOrNull(id)
    fun getAllStories(): List<Story> = storyRepository.findAll().toList()
    fun getStoriesFiltered(ratings: List<Rating>? = null, tags: List<StoryTag>? = null): List<Story> {
        var stories = storyRepository.findAll()

        if (ratings?.isNotEmpty() == true) {
            stories = stories.filter { story -> ratings.any { rating -> story.rating == rating } }
        }

        if (tags?.isNotEmpty() == true) {
            stories = stories.filter { story -> tags.all { tag -> story.storyTags.contains(tag) } }
        }

        return stories.toList()
    }

    @Transactional
    fun createStory(story: StoryDTO): Boolean {
        val (authors, artists, betas) = story.resolveUsersArtistsBetas()
        val chapters = story.resolveChapters()

        //only authors are required,
        if (authors.isEmpty()) return false

        val newStory = Story().updateStory(story, authors, chapters, artists, betas)

        //save chapters if there are any first
        newStory.chapters.forEach { chapterRepository.save(it) }
        storyRepository.save(newStory)

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

    fun deleteStory(id: Long): Boolean {
        val story = storyRepository.findByIdOrNull(id) ?: return false

        //remove all chapters
        story.chapters.forEach { chapter ->
            //remove all comments from chapter
            val comments = chapterCommentRepository.findByChapter(chapter)
            comments.forEach { comment ->
                chapterCommentRepository.delete(comment)
            }

            chapterRepository.delete(chapter)
        }
        storyRepository.delete(story)

        return true
    }

    fun addChapter(newChapter: ChapterDTO): Boolean {
        val story = storyRepository.findByIdOrNull(newChapter.story?.id ?: -1) ?: return false

        val (artists, betas) = newChapter.resolveArtistsBetas()

        val chapter = Chapter().also {
            it.id = newChapter.id
            it.title = newChapter.title
            it.summary = newChapter.summary
            it.startNotes = newChapter.startNotes
            it.endNotes = newChapter.endNotes
            it.content = newChapter.content
            it.publishedDate = newChapter.publishedDate
            it.artist = artists.toMutableList()
            it.beta = betas.toMutableList()
            it.storyBanner = newChapter.storyBanner
            it.story = story
        }

        story.chapters.add(chapter)
        chapterRepository.save(chapter)
        storyRepository.save(story)

        return true
    }

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
            it.artist = artists.toMutableList()
            it.beta = betas.toMutableList()
            it.storyBanner = chapter.storyBanner
        }

        chapterRepository.save(oldChapter)
        return true
    }

    fun deleteChapter(id: Long): Boolean {
        val chapter = chapterRepository.findByIdOrNull(id) ?: return false
        val story = chapter.story

        if (story != null) {
            story.chapters.remove(chapter)
            storyRepository.save(story)
        }

        chapterRepository.delete(chapter)
        return true
    }

    fun addContributor(storyId: Long, userId: Long, userTag: UserTag): Boolean {
        val story = storyRepository.findByIdOrNull(storyId) ?: return false
        val user = userRepository.findByIdOrNull(userId) ?: return false

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
            user.userTags.add(userTag)
            userRepository.save(user)
        }
        return true
    }

    //TODO: Chapter commenting stuff

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
        val authors = this.author.mapNotNull { userRepository.findByIdOrNull(it.id ?: -1) }
        val artists = this.artist.mapNotNull { userRepository.findByIdOrNull(it.id ?: -1) }
        val betas = this.beta.mapNotNull { userRepository.findByIdOrNull(it.id ?: -1) }

        return arrayOf(authors, artists, betas)
    }

    private fun ChapterDTO.resolveArtistsBetas(): Array<List<User>> {
        val artists = this.artist.mapNotNull { userRepository.findByIdOrNull(it.id ?: -1) }
        val betas = this.beta.mapNotNull { userRepository.findByIdOrNull(it.id ?: -1) }

        return arrayOf(artists, betas)
    }

    private fun StoryDTO.resolveChapters(): List<Chapter> =
        this.chapters.mapNotNull { chapterRepository.findByIdOrNull(it.id ?: -1) }
}
