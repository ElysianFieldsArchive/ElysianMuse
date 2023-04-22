//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[addChapterComment](add-chapter-comment.md)

# addChapterComment

[jvm]\

@PutMapping(value = [&quot;/chapter/{chapterId}/comment&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMEBR', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addChapterComment](add-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBodycomment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;
