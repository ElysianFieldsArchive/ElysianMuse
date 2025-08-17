//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)/[addChapter](add-chapter.md)

# addChapter

[jvm]\

@PutMapping(value = [&quot;/{storyId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addChapter](add-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@Validchapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;

Adds a [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md) to a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) identified by its id.

#### Return

ResponseEntity having HTTP status 200 OK or 400 BAD REQUEST; 401 UNAUTHORIZED if no valid authorization was provided

#### Parameters

jvm

| | |
|---|---|
| storyId | id of the story a chapter should be added to |
| chapter | [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md) containing the chapter to be added |