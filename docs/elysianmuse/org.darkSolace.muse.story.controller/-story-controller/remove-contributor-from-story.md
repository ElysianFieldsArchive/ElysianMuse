//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[removeContributorFromStory](remove-contributor-from-story.md)

# removeContributorFromStory

[jvm]\

@DeleteMapping(value = [&quot;/{storyId}/contributor&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [removeContributorFromStory](remove-contributor-from-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBodystoryContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
