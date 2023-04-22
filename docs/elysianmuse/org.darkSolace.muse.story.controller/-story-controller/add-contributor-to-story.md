//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[addContributorToStory](add-contributor-to-story.md)

# addContributorToStory

[jvm]\

@PutMapping(value = [&quot;/{storyId}/contributor&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMEBR', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addContributorToStory](add-contributor-to-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestParamuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), @RequestParamuserTag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
