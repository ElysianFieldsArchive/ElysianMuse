//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[getUserContributions](get-user-contributions.md)

# getUserContributions

[jvm]\

@GetMapping(value = [&quot;/{user}/contributions&quot;])

fun [getUserContributions](get-user-contributions.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?): ResponseEntity&lt;*&gt;

Retrieves a [Collection](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-collection/index.html) of [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md)s a given user has contributed to

#### Return

ResponseEntity with HTTP 200 or 400, depending on success. In case of success contains a [Collection](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-collection/index.html)     of [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md)s the user has contributed to

#### Parameters

jvm

| | |
|---|---|
| user | the user for which all contributions will be retrieveds |