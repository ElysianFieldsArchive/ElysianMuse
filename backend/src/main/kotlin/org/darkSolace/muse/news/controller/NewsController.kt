package org.darkSolace.muse.news.controller

import org.darkSolace.muse.news.model.dto.NewsCommentDTO
import org.darkSolace.muse.news.model.dto.NewsEntryDTO
import org.darkSolace.muse.news.service.NewsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/news")
class NewsController {
    @Autowired
    lateinit var newsService: NewsService

    @GetMapping("/last/{size}")
    fun getNewest(@PathVariable(required = false) size: Int = 3): List<NewsEntryDTO> {
        return NewsEntryDTO.fromList(newsService.getLast(size))
    }

    @GetMapping("/{id}")
    fun getNews(@PathVariable id: Long): ResponseEntity<*> {
        val news = newsService.getNews(id) ?: return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        return ResponseEntity<NewsEntryDTO>(NewsEntryDTO(news), HttpStatus.OK)
    }

    @GetMapping
    fun getAllNews(): List<NewsEntryDTO> {
        return NewsEntryDTO.fromList(newsService.getAllNews())
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')")
    fun postNews(@RequestBody news: NewsEntryDTO): ResponseEntity<Unit> {
        val success = newsService.createNews(news)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')")
    fun editNews(@PathVariable id: Long, @RequestBody news: NewsEntryDTO): ResponseEntity<Unit> {
        val success = newsService.editNews(id, news)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    @PutMapping("/{id}/comment")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addComment(@PathVariable id: Long, @RequestBody comment: NewsCommentDTO): ResponseEntity<Unit> {
        val success = newsService.addCommentToNews(id, comment)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/comment/{commentId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun editComment(
        @PathVariable commentId: Long,
        @RequestBody commentDto: NewsCommentDTO
    ): ResponseEntity<Unit> {
        val success = newsService.editComment(commentId, commentDto)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }
}