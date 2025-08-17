//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)/[editChapterComment](edit-chapter-comment.md)

# editChapterComment

[jvm]\

@PostMapping(value = [&quot;/chapter/{chapterId}/comment&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [editChapterComment](edit-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@ValideditedComment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Edit a comment to the specified chapter

#### Return

ResponseEntity having HTTP status 200 OK or 400 BAD REQUEST; 401 UNAUTHORIZED if no valid     * authorization was provided

#### Parameters

jvm

| | |
|---|---|
| chapterId | the id of the [org.darkSolace.muse.story.model.ChapterComment](../../org.darkSolace.muse.story.model/-chapter-comment/index.md) which has the comment to     be edited |
| editedComment | the [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md) containing the edited comment |