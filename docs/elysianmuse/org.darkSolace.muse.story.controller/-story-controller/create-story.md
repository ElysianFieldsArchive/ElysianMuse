//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[createStory](create-story.md)

# createStory

[jvm]\

@PutMapping

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [createStory](create-story.md)(@RequestBodystory: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
