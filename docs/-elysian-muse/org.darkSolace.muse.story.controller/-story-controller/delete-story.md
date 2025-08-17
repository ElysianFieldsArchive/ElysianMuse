//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[deleteStory](delete-story.md)

# deleteStory

[jvm]\

@DeleteMapping(value = [&quot;/{id}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [deleteStory](delete-story.md)(@PathVariableid: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Edits an existing [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md)

#### Return

ResponseEntity with HTTP 200 or 400, depending on success

#### Parameters

jvm

| | |
|---|---|
| id | id of the [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) to be deleted |