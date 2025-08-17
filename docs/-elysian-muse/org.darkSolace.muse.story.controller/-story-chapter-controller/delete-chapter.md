//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)/[deleteChapter](delete-chapter.md)

# deleteChapter

[jvm]\

@DeleteMapping(value = [&quot;/{storyId}/{chapterId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [deleteChapter](delete-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;*&gt;

Delete an existing [org.darkSolace.muse.story.model.Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md) of a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md), both identified by their ids.

#### Return

ResponseEntity having HTTP status 200 OK or 400 BAD REQUEST; 401 UNAUTHORIZED if no valid authorization was provided

#### Parameters

jvm

| | |
|---|---|
| storyId | id of the [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) a chapter should be removed from |
| chapterId | id of the [org.darkSolace.muse.story.model.Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md) to be removed |