//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[addContributorToChapter](add-contributor-to-chapter.md)

# addContributorToChapter

[jvm]\

@PutMapping(value = [&quot;/{storyId}/{chapterId}/contributor&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addContributorToChapter](add-contributor-to-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBodychapterContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
