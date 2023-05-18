//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[editChapterComment](edit-chapter-comment.md)

# editChapterComment

[jvm]\

@PostMapping(value = [&quot;/chapter/{chapterId}/comment&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMEBR', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [editChapterComment](edit-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestParam@ValideditedComment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
