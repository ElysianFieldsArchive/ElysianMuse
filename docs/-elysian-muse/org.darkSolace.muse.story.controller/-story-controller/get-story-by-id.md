//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[getStoryById](get-story-by-id.md)

# getStoryById

[jvm]\

@GetMapping(value = [&quot;/{id}&quot;])

fun [getStoryById](get-story-by-id.md)(@PathVariableid: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)): ResponseEntity&lt;*&gt;

Retrieves a [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md) by its id

#### Return

ResponseEntity containing a [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md) or empty in case of HttpStatus.NOT_FOUND

#### Parameters

jvm

| | |
|---|---|
| id | id of the story |