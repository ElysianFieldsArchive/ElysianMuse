//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryChapterController](index.md)/[removeContributorFromChapter](remove-contributor-from-chapter.md)

# removeContributorFromChapter

[jvm]\

@DeleteMapping(value = [&quot;/{storyId}/{chapterId}/contributor&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [removeContributorFromChapter](remove-contributor-from-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBodychapterContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Removes a contributor from a chapter.

#### Return

ResponseEntity having HTTP status 200 OK or 400 BAD REQUEST; 401 UNAUTHORIZED if no valid authorization was provided

#### Parameters

jvm

| | |
|---|---|
| storyId | id identifying the [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) |
| chapterId | id identifying the [org.darkSolace.muse.story.model.Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md) |
| chapterContributorDTO | [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md) holding the [org.darkSolace.muse.user.model.User](../../org.darkSolace.muse.user.model/-user/index.md) id and [org.darkSolace.muse.user.model.UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) |