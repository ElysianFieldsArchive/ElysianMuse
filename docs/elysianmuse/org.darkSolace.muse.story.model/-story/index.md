//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.model](../index.md)/[Story](index.md)

# Story

[jvm]\
@Entity

class [Story](index.md)

## Constructors

|                    |                                   |
|--------------------|-----------------------------------|
| [Story](-story.md) | [jvm]<br>fun [Story](-story.md)() |

## Properties

| Name                                       | Summary                                                                                                                                                                                                                        |
|--------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [artist](artist.md)                        | [jvm]<br>var [artist](artist.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;                   |
| [author](author.md)                        | [jvm]<br>var [author](author.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;                   |
| [beta](beta.md)                            | [jvm]<br>var [beta](beta.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;                       |
| [chapters](chapters.md)                    | [jvm]<br>var [chapters](chapters.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Chapter](../-chapter/index.md)&gt;                                           |
| [commentModeration](comment-moderation.md) | [jvm]<br>var [commentModeration](comment-moderation.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false                                                                            |
| [completed](completed.md)                  | [jvm]<br>var [completed](completed.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false                                                                                             |
| [eventId](event-id.md)                     | [jvm]<br>var [eventId](event-id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = 0                                                                                                          |
| [favorites](favorites.md)                  | [jvm]<br>var [favorites](favorites.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;             |
| [favouritesCount](favourites-count.md)     | [jvm]<br>@[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)<br>var [favouritesCount](favourites-count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [goalChapterCount](goal-chapter-count.md)  | [jvm]<br>var [goalChapterCount](goal-chapter-count.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = 0                                                                                       |
| [hitCount](hit-count.md)                   | [jvm]<br>@[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)<br>var [hitCount](hit-count.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)             |
| [id](id.md)                                | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null                                                                                                                 |
| [kudoCount](kudo-count.md)                 | [jvm]<br>@[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)<br>var [kudoCount](kudo-count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)             |
| [publishedDate](published-date.md)         | [jvm]<br>var [publishedDate](published-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)                                                                                                         |
| [rating](rating.md)                        | [jvm]<br>var [rating](rating.md): [Rating](../-rating/index.md)                                                                                                                                                                |
| [storyBanner](story-banner.md)             | [jvm]<br>var [storyBanner](story-banner.md): [Banner](../-banner/index.md)? = null                                                                                                                                             |
| [storyNotes](story-notes.md)               | [jvm]<br>var [storyNotes](story-notes.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)                                                                                                    |
| [storyTags](story-tags.md)                 | [jvm]<br>var [storyTags](story-tags.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[StoryTag](../-story-tag/index.md)&gt;                                     |
| [summary](summary.md)                      | [jvm]<br>var [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)                                                                                                           |
| [title](title.md)                          | [jvm]<br>var [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)                                                                                                               |
| [wordCount](word-count.md)                 | [jvm]<br>@[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)<br>var [wordCount](word-count.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)           |
