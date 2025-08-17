//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)/[editChapter](edit-chapter.md)

# editChapter

[jvm]\

@PostMapping(value = [&quot;/{storyId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [editChapter](edit-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@ValideditedChapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;

Edits an existing [org.darkSolace.muse.story.model.Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md) of a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) identified by its id.

#### Return

ResponseEntity having HTTP status 200 OK or 400 BAD REQUEST; 401 UNAUTHORIZED if no valid authorization was provided

#### Parameters

jvm

| | |
|---|---|
| storyId | id of the story a chapter should be added to |
| editedChapter | [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md) containing the edited chapter |