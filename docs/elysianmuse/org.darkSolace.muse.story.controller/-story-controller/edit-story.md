//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[editStory](edit-story.md)

# editStory

[jvm]\

@PostMapping(value = [&quot;/&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [editStory](edit-story.md)(@RequestBodyeditedStory: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
