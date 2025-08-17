//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)/[deleteChapterComment](delete-chapter-comment.md)

# deleteChapterComment

[jvm]\

@DeleteMapping(value = [&quot;/chapter/{chapterId}/comment/{commentId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [deleteChapterComment](delete-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @PathVariablecommentId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Delete a comment to the specified chapter

#### Return

ResponseEntity having HTTP status 200 OK or 400 BAD REQUEST; 401 UNAUTHORIZED if no valid     authorization was provided

#### Parameters

jvm

| | |
|---|---|
| chapterId | the id of the [org.darkSolace.muse.story.model.Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md) to remove the comment from |
| commentId | the id of the [org.darkSolace.muse.story.model.ChapterComment](../../org.darkSolace.muse.story.model/-chapter-comment/index.md) to be removed |