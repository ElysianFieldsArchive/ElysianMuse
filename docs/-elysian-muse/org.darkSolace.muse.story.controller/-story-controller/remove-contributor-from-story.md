//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[removeContributorFromStory](remove-contributor-from-story.md)

# removeContributorFromStory

[jvm]\

@DeleteMapping(value = [&quot;/{storyId}/contributor&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [removeContributorFromStory](remove-contributor-from-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBodystoryContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Removes the contribution of a [org.darkSolace.muse.user.model.User](../../org.darkSolace.muse.user.model/-user/index.md) from a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md)

#### Return

ResponseEntity with HTTP 200 or 400, depending on success

#### Parameters

jvm

| | |
|---|---|
| storyId | id of the [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) for which the contribution should be removed     from |
| storyContributorDTO | dto holding the user and type of contribution to be removed |