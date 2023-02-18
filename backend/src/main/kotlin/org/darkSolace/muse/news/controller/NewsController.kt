package org.darkSolace.muse.news.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.darkSolace.muse.news.model.NewsComment
import org.darkSolace.muse.news.model.NewsEntry
import org.darkSolace.muse.news.model.dto.NewsCommentDTO
import org.darkSolace.muse.news.model.dto.NewsEntryDTO
import org.darkSolace.muse.news.service.NewsService
import org.darkSolace.muse.security.model.UserDetails
import org.darkSolace.muse.user.model.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

/**
 * RestController defining endpoints regarding news
 */
@RestController
@RequestMapping("/api/news")
@Validated
class NewsController {
    @Autowired
    lateinit var newsService: NewsService

    /**
     * Retrieves the last 3 news stories. Listens on /api/news/last.
     *
     * @sample `curl localhost:8080/api/news/last`
     * @return List of [NewsEntryDTO]s. Max size 3.
     */
    @GetMapping("/last")
    fun getNewestThree(): List<NewsEntryDTO> {
        return NewsEntryDTO.fromList(newsService.getLast(3))
    }

    /**
     * Retrieves the last 'size' news stories. Listens on /api/news/last/{size}.
     *
     * @sample `curl localhost:8080/api/news/last/5`
     * @param size Number of news entries to retrieve. Defaults to 3.
     * @return List of [NewsEntryDTO]s. Defaults to size 3.
     */
    @GetMapping("/last/{size}")
    fun getNewest(@Valid @PathVariable(required = false) @Min(1) size: Int = 3): List<NewsEntryDTO> {
        return NewsEntryDTO.fromList(newsService.getLast(size))
    }

    /**
     * Retrieves a [NewsEntryDTO] by its id. Listens on /api/news/{id}.
     *
     * @sample `curl localhost:8080/api/news/5`
     * @param id the news id
     * @return the retrieved [NewsEntryDTO] or `null`
     */
    @GetMapping("/{id}")
    fun getNews(@PathVariable id: Long): ResponseEntity<*> {
        val news = newsService.getNews(id) ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        return ResponseEntity<NewsEntryDTO>(NewsEntryDTO.from(news), HttpStatus.OK)
    }

    /**
     * Retrieves all [NewsEntryDTO]s. Listens on /api/news.
     *
     * @sample `curl localhost:8080/api/news`
     * @return the retrieved [NewsEntryDTO], might be empty
     */
    @GetMapping
    fun getAllNews(): List<NewsEntryDTO> {
        return NewsEntryDTO.fromList(newsService.getAllNews())
    }

    /**
     * Adds a news entry.
     * To add news the [org.darkSolace.muse.user.model.Role] of
     * [org.darkSolace.muse.user.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.user.model.Role.MODERATOR] is required.
     *
     * @sample `curl -X PUT -H "Authorization: [...]" -d "..." localhost:8080/api/news`
     * @param news the [NewsEntryDTO] containing the news to be added
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')")
    fun postNews(
        @RequestBody news: NewsEntryDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        if ((authentication?.principal as UserDetails?)?.user?.username != news.author?.username)
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)

        val success = newsService.createNews(news)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    /**
     * Edits a news entry.
     * To edit news the [org.darkSolace.muse.user.model.Role] of
     * [org.darkSolace.muse.user.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.user.model.Role.MODERATOR] is required.
     *
     * @sample `curl -X PUT -H "Authorization: [...]" -d "..." localhost:8080/api/news/{id}`
     * @param news the [NewsEntryDTO] containing the edited news
     * @param id the id of the [NewsEntryDTO] to be edited
     */
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')")
    fun editNews(
        @PathVariable id: Long,
        @RequestBody news: NewsEntryDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        if ((authentication?.principal as UserDetails?)?.user?.username != news.author?.username)
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
        val success = newsService.editNews(id, news)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    /**
     * Adds a comment to a news entry.
     * To add a comment the [org.darkSolace.muse.user.model.Role] of
     * [org.darkSolace.muse.user.model.Role.MEMBER],
     * [org.darkSolace.muse.user.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.user.model.Role.MODERATOR] is required.
     *
     * @sample `curl -X PUT -H "Authorization: [...]" -d "..." localhost:8080/api/news/{id}/comment`
     * @param id the id of the [NewsEntryDTO] to be edited
     * @param commentDto the [NewsCommentDTO] containing the comment to be added
     */
    @PutMapping("/{id}/comment")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addComment(
        @PathVariable id: Long,
        @RequestBody commentDto: NewsCommentDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        if ((authentication?.principal as UserDetails?)?.user?.username != commentDto.author?.username)
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)

        val success = newsService.addCommentToNews(id, commentDto)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    /**
     * Edits a comment on a news entry.
     * To edit a comment the [org.darkSolace.muse.user.model.Role] of
     * [org.darkSolace.muse.user.model.Role.MEMBER],
     * [org.darkSolace.muse.user.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.user.model.Role.MODERATOR] is required.
     *
     * @sample `curl -X POST -H "Authorization: [...]" -d "..." localhost:8080/api/news/comment/{id}`
     * @param commentId the id of the comment to be edited
     * @param commentDto the [NewsCommentDTO] containing the edited comment
     */
    @PostMapping("/comment/{commentId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun editComment(
        @PathVariable commentId: Long,
        @RequestBody commentDto: NewsCommentDTO,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        if ((authentication?.principal as UserDetails?)?.user?.username != commentDto.author?.username)
            return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)

        val success = newsService.editComment(commentId, commentDto)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    /**
     * Deletes the specified [NewsEntry]. Can only be called by a user with role [org.darkSolace.muse.user.model.Role.ADMINISTRATOR]
     *
     * @param newsId id of the [NewsEntry] to be deleted
     */
    @DeleteMapping("/{newsId}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    fun deleteNews(
        @PathVariable newsId: Long,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        val success = newsService.deleteNews(newsId)
        return if (success) {
            ResponseEntity<Unit>(HttpStatus.OK)
        } else {
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        }
    }


    /**
     * Deletes the specified [NewsComment]. Can be called by a user with role [Role.MODERATOR], [Role.ADMINISTRATOR] or
     * the author itself.
     *
     * @param commentId id of the [NewsComment] to be deleted
     */
    @DeleteMapping("/comment/{commentId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun deleteComment(
        @PathVariable commentId: Long,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        //check if comment belongs to user, or user is admin/mod
        val user = (authentication?.principal as UserDetails?)?.user
        val comment = newsService.newsCommentRepository.findById(commentId)
        val success = if (user?.role in listOf(Role.ADMINISTRATOR, Role.MODERATOR) ||
            comment.getOrNull()?.author?.id == user?.id
        ) {
            newsService.deleteComment(commentId)
        } else {
            false
        }

        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }
}
