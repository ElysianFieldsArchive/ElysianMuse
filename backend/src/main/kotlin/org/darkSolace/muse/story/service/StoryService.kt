package org.darkSolace.muse.story.service

import jakarta.transaction.Transactional
import org.darkSolace.muse.story.model.Chapter
import org.darkSolace.muse.story.model.Rating
import org.darkSolace.muse.story.model.Story
import org.darkSolace.muse.story.model.StoryTag
import org.darkSolace.muse.story.model.dto.StoryDTO
import org.darkSolace.muse.story.repository.ChapterCommentRepository
import org.darkSolace.muse.story.repository.ChapterRepository
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.story.repository.StoryTagRepository
import org.darkSolace.muse.user.model.User
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

    @Transactional
    fun getAllStories(): List<Story> = storyRepository.findAll().toList()

    @Transactional
    fun getStoriesFiltered(
        ratings: List<Rating>? = null, tags: List<StoryTag>? = null, authors: List<User>? = null,
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
            val chapter = Chapter().also { chapter ->
                chapter.title = chapterDTO.title
                chapter.summary = chapterDTO.summary
                chapter.startNotes = chapterDTO.startNotes
                chapter.endNotes = chapterDTO.endNotes
                chapter.content = chapterDTO.content
                chapter.publishedDate = Date()
                chapter.updatedDate = Date()
                chapter.beta = chapterDTO.beta.mapNotNull { userService.getById(it.id ?: -1) }.toMutableSet()
                chapter.artist = chapterDTO.artist.mapNotNull { userService.getById(it.id ?: -1) }.toMutableSet()
                chapter.storyBanner = chapterDTO.storyBanner
                chapter.storyId = newStory.id
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

    private fun Story.updateStory(
        changes: StoryDTO,
        authors: List<User>,
        chapters: List<Chapter>,
        artists: List<User> = emptyList(),
        betas: List<User> = emptyList(),
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
        val authors = userService.getByIds(this.author.mapNotNull { it.id })
        val artists = userService.getByIds(this.artist.mapNotNull { it.id })
        val betas = userService.getByIds(this.beta.mapNotNull { it.id })

        return arrayOf(authors, artists, betas)
    }

    private fun StoryDTO.resolveChapters(): List<Chapter> =
        chapterRepository.findAllById(this.chapters.mapNotNull { it.id }).toList()
}
