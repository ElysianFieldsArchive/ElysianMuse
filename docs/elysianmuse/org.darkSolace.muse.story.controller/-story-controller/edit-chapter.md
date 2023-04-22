//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[editChapter](edit-chapter.md)

# editChapter

[jvm]\

@PostMapping(value = [&quot;/{storyId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMEBR', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [editChapter](edit-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBodyeditedChapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;
