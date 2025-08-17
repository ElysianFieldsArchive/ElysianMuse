//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.service](../index.md)/[StoryContributionService](index.md)

# StoryContributionService

[jvm]\
@Service

class [StoryContributionService](index.md)(@AutowiredstoryRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @AutowireduserService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md), @AutowiredstoryService: [StoryService](../-story-service/index.md))

## Constructors

| | |
|---|---|
| [StoryContributionService](-story-contribution-service.md) | [jvm]<br>constructor(@AutowiredstoryRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @AutowireduserService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md), @AutowiredstoryService: [StoryService](../-story-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addContributorToStory](add-contributor-to-story.md) | [jvm]<br>fun [addContributorToStory](add-contributor-to-story.md)(storyId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userTag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [getUserContributions](get-user-contributions.md) | [jvm]<br>fun [getUserContributions](get-user-contributions.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [UserContributionDTO](../../org.darkSolace.muse.story.model.dto/-user-contribution-d-t-o/index.md) |
| [removeContributorFromStory](remove-contributor-from-story.md) | [jvm]<br>fun [removeContributorFromStory](remove-contributor-from-story.md)(storyId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userTag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |