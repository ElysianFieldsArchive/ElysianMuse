//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[addContributorToStory](add-contributor-to-story.md)

# addContributorToStory

[jvm]\

@PutMapping(value = [&quot;/{storyId}/contributor&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addContributorToStory](add-contributor-to-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBodystoryContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Adds a [org.darkSolace.muse.user.model.User](../../org.darkSolace.muse.user.model/-user/index.md) as a contributor to a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md)

#### Return

ResponseEntity with HTTP 200 or 400, depending on success

#### Parameters

jvm

| | |
|---|---|
| storyId | id of the [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) the contributor should be added to |
| storyContributorDTO | dto holding the user and type of contribution |