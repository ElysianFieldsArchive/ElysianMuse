//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[createStory](create-story.md)

# createStory

[jvm]\

@PutMapping

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [createStory](create-story.md)(@RequestBody@Validstory: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Creates a new [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md)

#### Return

ResponseEntity with HTTP 200 or 400, depending on success

#### Parameters

jvm

| | |
|---|---|
| story | [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md) containing the new story |