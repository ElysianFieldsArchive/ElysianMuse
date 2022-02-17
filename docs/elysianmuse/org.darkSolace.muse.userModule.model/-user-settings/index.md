//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.model](../index.md)/[UserSettings](index.md)

# UserSettings

[jvm]\
@Entity

data class [UserSettings](index.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, selectedLayout: [Layout](../../org.darkSolace.muse.layoutModule.model/-layout/index.md)?, emailVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), birthdayVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), realNameVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), maxRating: [Rating](../../org.darkSolace.muse.storyModule.model/-rating/index.md), showWarningForTags: [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[StoryTag](../../org.darkSolace.muse.storyModule.model/-story-tag/index.md)&gt;, shareButtonsVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), showEntireStories: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), selectedFontFamily: [FontFamily](../../org.darkSolace.muse.layoutModule.model/-font-family/index.md), storyBannersVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), selectedFontSize: [FontSize](../../org.darkSolace.muse.layoutModule.model/-font-size/index.md))

The [UserSettings](index.md) model class, is part of a [User](../-user/index.md).

Holds user configurations, e.g. which values should be visible on the user profile as well as default content filter/warning settings.

## See also

jvm

| | |
|---|---|
| [org.darkSolace.muse.userModule.model.User](../-user/index.md) |  |

## Constructors

| | |
|---|---|
| [UserSettings](-user-settings.md) | [jvm]<br>fun [UserSettings](-user-settings.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, selectedLayout: [Layout](../../org.darkSolace.muse.layoutModule.model/-layout/index.md)? = null, emailVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, birthdayVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, realNameVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, maxRating: [Rating](../../org.darkSolace.muse.storyModule.model/-rating/index.md) = Rating.PARENTAL_GUIDANCE_13, showWarningForTags: [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[StoryTag](../../org.darkSolace.muse.storyModule.model/-story-tag/index.md)&gt; = mutableSetOf(), shareButtonsVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, showEntireStories: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, selectedFontFamily: [FontFamily](../../org.darkSolace.muse.layoutModule.model/-font-family/index.md) = FontFamily.SANS, storyBannersVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, selectedFontSize: [FontSize](../../org.darkSolace.muse.layoutModule.model/-font-size/index.md) = FontSize.MEDIUM) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [birthdayVisible](birthday-visible.md) | [jvm]<br>var [birthdayVisible](birthday-visible.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [emailVisible](email-visible.md) | [jvm]<br>var [emailVisible](email-visible.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [id](id.md) | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [maxRating](max-rating.md) | [jvm]<br>var [maxRating](max-rating.md): [Rating](../../org.darkSolace.muse.storyModule.model/-rating/index.md) |
| [realNameVisible](real-name-visible.md) | [jvm]<br>var [realNameVisible](real-name-visible.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [selectedFontFamily](selected-font-family.md) | [jvm]<br>var [selectedFontFamily](selected-font-family.md): [FontFamily](../../org.darkSolace.muse.layoutModule.model/-font-family/index.md) |
| [selectedFontSize](selected-font-size.md) | [jvm]<br>var [selectedFontSize](selected-font-size.md): [FontSize](../../org.darkSolace.muse.layoutModule.model/-font-size/index.md) |
| [selectedLayout](selected-layout.md) | [jvm]<br>var [selectedLayout](selected-layout.md): [Layout](../../org.darkSolace.muse.layoutModule.model/-layout/index.md)? = null |
| [shareButtonsVisible](share-buttons-visible.md) | [jvm]<br>var [shareButtonsVisible](share-buttons-visible.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
| [showEntireStories](show-entire-stories.md) | [jvm]<br>var [showEntireStories](show-entire-stories.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [showWarningForTags](show-warning-for-tags.md) | [jvm]<br>var [showWarningForTags](show-warning-for-tags.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[StoryTag](../../org.darkSolace.muse.storyModule.model/-story-tag/index.md)&gt; |
| [storyBannersVisible](story-banners-visible.md) | [jvm]<br>var [storyBannersVisible](story-banners-visible.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
