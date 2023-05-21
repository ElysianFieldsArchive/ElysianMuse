//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)

# StoryController

[jvm]\
@RestController

@RequestMapping(value = [&quot;/api/story&quot;])

class [StoryController](index.md)(@AutowiredstoryService: [StoryService](../../org.darkSolace.muse.story.service/-story-service/index.md), @AutowiredstoryContributionService: [StoryContributionService](../../org.darkSolace.muse.story.service/-story-contribution-service/index.md))

## Constructors

| | |
|---|---|
| [StoryController](-story-controller.md) | [jvm]<br>constructor(@AutowiredstoryService: [StoryService](../../org.darkSolace.muse.story.service/-story-service/index.md), @AutowiredstoryContributionService: [StoryContributionService](../../org.darkSolace.muse.story.service/-story-contribution-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addContributorToStory](add-contributor-to-story.md) | [jvm]<br>@PutMapping(value = [&quot;/{storyId}/contributor&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [addContributorToStory](add-contributor-to-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBodystoryContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |
| [createStory](create-story.md) | [jvm]<br>@PutMapping<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [createStory](create-story.md)(@RequestBody@Validstory: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |
| [deleteStory](delete-story.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{id}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [deleteStory](delete-story.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |
| [editStory](edit-story.md) | [jvm]<br>@PostMapping(value = [&quot;/&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [editStory](edit-story.md)(@RequestBody@ValideditedStory: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |
| [getAllStories](get-all-stories.md) | [jvm]<br>@GetMapping(value = [&quot;/all&quot;])<br>fun [getAllStories](get-all-stories.md)(): [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)&gt; |
| [getStoriesFiltered](get-stories-filtered.md) | [jvm]<br>@PostMapping(value = [&quot;/filtered&quot;])<br>fun [getStoriesFiltered](get-stories-filtered.md)(@RequestBodyfilters: [FilterStoriesDTO](../../org.darkSolace.muse.story.model.dto/-filter-stories-d-t-o/index.md)): [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)&gt; |
| [getStoryById](get-story-by-id.md) | [jvm]<br>@GetMapping(value = [&quot;/{id}&quot;])<br>fun [getStoryById](get-story-by-id.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ResponseEntity&lt;*&gt; |
| [getUserContributions](get-user-contributions.md) | [jvm]<br>@GetMapping(value = [&quot;/{user}/contributions&quot;])<br>fun [getUserContributions](get-user-contributions.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?): ResponseEntity&lt;*&gt; |
| [removeContributorFromStory](remove-contributor-from-story.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{storyId}/contributor&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [removeContributorFromStory](remove-contributor-from-story.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBodystoryContributorDTO: [StoryChapterContributorDTO](../../org.darkSolace.muse.story.model.dto/-story-chapter-contributor-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |
