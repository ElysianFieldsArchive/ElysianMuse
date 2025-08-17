//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)

# StoryChapterController

@RestController

@RequestMapping(value = [&quot;/api/story&quot;])

class [StoryChapterController](index.md)(@AutowiredstoryService: [StoryService](../../org.darkSolace.muse.story.service/-story-service/index.md), @AutowiredchapterService: [StoryChapterService](../../org.darkSolace.muse.story.service/-story-chapter-service/index.md))

RestController defining endpoints regarding all story chapter activity. Same endpoint as [StoryController](../-story-controller/index.md)

#### See also

| |
|---|
| [StoryController](../-story-controller/index.md) |

## Constructors

| | |
|---|---|
| [StoryChapterController](-story-chapter-controller.md) | [jvm]<br>constructor(@AutowiredstoryService: [StoryService](../../org.darkSolace.muse.story.service/-story-service/index.md), @AutowiredchapterService: [StoryChapterService](../../org.darkSolace.muse.story.service/-story-chapter-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addChapter](add-chapter.md) | [jvm]<br>@PutMapping(value = [&quot;/{storyId}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [addChapter](add-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@Validchapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;<br>Adds a [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md) to a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) identified by its id. |
| [addChapterComment](add-chapter-comment.md) | [jvm]<br>@PutMapping(value = [&quot;/chapter/{chapterId}/comment&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [addChapterComment](add-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@Validcomment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;<br>Adds a comment to the specified chapter |
| [addContributorToChapter](add-contributor-to-chapter.md) | [jvm]<br>@PutMapping(value = [&quot;/{storyId}/{chapterId}/contributor&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [addContributorToChapter](add-contributor-to-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBodychapterContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Adds a contributor to a chapter. |
| [deleteChapter](delete-chapter.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{storyId}/{chapterId}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [deleteChapter](delete-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;*&gt;<br>Delete an existing [org.darkSolace.muse.story.model.Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md) of a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md), both identified by their ids. |
| [deleteChapterComment](delete-chapter-comment.md) | [jvm]<br>@DeleteMapping(value = [&quot;/chapter/{chapterId}/comment/{commentId}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [deleteChapterComment](delete-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @PathVariablecommentId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Delete a comment to the specified chapter |
| [editChapter](edit-chapter.md) | [jvm]<br>@PostMapping(value = [&quot;/{storyId}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [editChapter](edit-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@ValideditedChapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;*&gt;<br>Edits an existing [org.darkSolace.muse.story.model.Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md) of a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) identified by its id. |
| [editChapterComment](edit-chapter-comment.md) | [jvm]<br>@PostMapping(value = [&quot;/chapter/{chapterId}/comment&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [editChapterComment](edit-chapter-comment.md)(@PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBody@ValideditedComment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Edit a comment to the specified chapter |
| [removeContributorFromChapter](remove-contributor-from-chapter.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{storyId}/{chapterId}/contributor&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [removeContributorFromChapter](remove-contributor-from-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBodychapterContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Removes a contributor from a chapter. |