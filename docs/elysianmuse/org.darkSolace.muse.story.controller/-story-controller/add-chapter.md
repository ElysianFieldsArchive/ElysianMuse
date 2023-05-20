//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[addChapter](add-chapter.md)

# addChapter

[jvm]\

@PutMapping(value = [&quot;/{storyId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addChapter](add-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBody@Validchapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;
