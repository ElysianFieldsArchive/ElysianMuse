//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[deleteChapter](delete-chapter.md)

# deleteChapter

[jvm]\

@DeleteMapping(value = [&quot;/{storyId}/{chapterId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [deleteChapter](delete-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;*&gt;
