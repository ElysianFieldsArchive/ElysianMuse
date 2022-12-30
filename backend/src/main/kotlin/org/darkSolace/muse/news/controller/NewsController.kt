package org.darkSolace.muse.news.controller

import org.darkSolace.muse.news.model.dto.NewsCommentDto
import org.darkSolace.muse.news.model.dto.NewsEntryDto
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
    fun getNewest(@PathVariable size: Int = 3): List<NewsEntryDto> {
        return NewsEntryDto.fromList(newsService.getLast(size))
    }

    @PutMapping("/{id}/comment")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun addComment(@PathVariable id: Long, @RequestBody comment: NewsCommentDto): ResponseEntity<Unit> {
        val success = newsService.addCommentToNews(id, comment)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')")
    fun postNews(@RequestBody news : NewsEntryDto): ResponseEntity<Unit> {
        val success = newsService.createNews(news)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }
}