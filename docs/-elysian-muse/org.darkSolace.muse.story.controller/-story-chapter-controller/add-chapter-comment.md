//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)/[addChapterComment](add-chapter-comment.md)

# addChapterComment

[jvm]\

@PutMapping(value = [&quot;/chapter/{chapterId}/comment&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addChapterComment](add-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@Validcomment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;

Adds a comment to the specified chapter

#### Return

ResponseEntity having HTTP status 200 OK or 400 BAD REQUEST; 401 UNAUTHORIZED if no valid     * authorization was provided

#### Parameters

jvm

| | |
|---|---|
| chapterId | the id of the [org.darkSolace.muse.story.model.ChapterComment](../../org.darkSolace.muse.story.model/-chapter-comment/index.md) to add the comment to |
| comment | the [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md) |