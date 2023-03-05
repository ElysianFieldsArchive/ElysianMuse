//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.repository](../index.md)/[UserRepository](index.md)/[findAllByUserTagsContains](find-all-by-user-tags-contains.md)

# findAllByUserTagsContains

[jvm]\
abstract fun [findAllByUserTagsContains](find-all-by-user-tags-contains.md)(userTags: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;

Retrieves all [User](../../org.darkSolace.muse.user.model/-user/index.md)s with a given [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) from the database.

#### Return

a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [User](../../org.darkSolace.muse.user.model/-user/index.md)s having the required [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)

#### Parameters

jvm

| | |
|---|---|
| userTags | the required [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)s |
