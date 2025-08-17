//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)

# StoryController

@RestController

@RequestMapping(value = [&quot;/api/story&quot;])

class [StoryController](index.md)(@AutowiredstoryService: [StoryService](../../org.darkSolace.muse.story.service/-story-service/index.md), @AutowiredstoryContributionService: [StoryContributionService](../../org.darkSolace.muse.story.service/-story-contribution-service/index.md))

RestController defining endpoints regarding all story activity. Same endpoint as [StoryChapterController](../-story-chapter-controller/index.md)

#### See also

| |
|---|
| [StoryChapterController](../-story-chapter-controller/index.md) |

## Constructors

| | |
|---|---|
| [StoryController](-story-controller.md) | [jvm]<br>constructor(@AutowiredstoryService: [StoryService](../../org.darkSolace.muse.story.service/-story-service/index.md), @AutowiredstoryContributionService: [StoryContributionService](../../org.darkSolace.muse.story.service/-story-contribution-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addContributorToStory](add-contributor-to-story.md) | [jvm]<br>@PutMapping(value = [&quot;/{storyId}/contributor&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [addContributorToStory](add-contributor-to-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBodystoryContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Adds a [org.darkSolace.muse.user.model.User](../../org.darkSolace.muse.user.model/-user/index.md) as a contributor to a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) |
| [createStory](create-story.md) | [jvm]<br>@PutMapping<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [createStory](create-story.md)(@RequestBody@Validstory: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Creates a new [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) |
| [deleteStory](delete-story.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{id}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [deleteStory](delete-story.md)(@PathVariableid: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Edits an existing [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) |
| [editStory](edit-story.md) | [jvm]<br>@PostMapping(value = [&quot;/&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [editStory](edit-story.md)(@RequestBody@ValideditedStory: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Edits an existing [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) |
| [getAllStories](get-all-stories.md) | [jvm]<br>@GetMapping(value = [&quot;/all&quot;])<br>fun [getAllStories](get-all-stories.md)(): [Collection](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-collection/index.html)&lt;[StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)&gt;<br>Retrieves all [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)s |
| [getStoriesFiltered](get-stories-filtered.md) | [jvm]<br>@PostMapping(value = [&quot;/filtered&quot;])<br>fun [getStoriesFiltered](get-stories-filtered.md)(@RequestBodyfilters: [FilterStoriesDTO](../../org.darkSolace.muse.story.model.dto/-filter-stories-d-t-o/index.md)): [Collection](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-collection/index.html)&lt;[StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)&gt;<br>Retrieves a list of [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)s, filtered by the provided filters |
| [getStoryById](get-story-by-id.md) | [jvm]<br>@GetMapping(value = [&quot;/{id}&quot;])<br>fun [getStoryById](get-story-by-id.md)(@PathVariableid: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)): ResponseEntity&lt;*&gt;<br>Retrieves a [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md) by its id |
| [getUserContributions](get-user-contributions.md) | [jvm]<br>@GetMapping(value = [&quot;/{user}/contributions&quot;])<br>fun [getUserContributions](get-user-contributions.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?): ResponseEntity&lt;*&gt;<br>Retrieves a [Collection](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-collection/index.html) of [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md)s a given user has contributed to |
| [removeContributorFromStory](remove-contributor-from-story.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{storyId}/contributor&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [removeContributorFromStory](remove-contributor-from-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), @RequestBodystoryContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Removes the contribution of a [org.darkSolace.muse.user.model.User](../../org.darkSolace.muse.user.model/-user/index.md) from a [org.darkSolace.muse.story.model.Story](../../org.darkSolace.muse.story.model/-story/index.md) |