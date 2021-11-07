//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.repository](../index.md)/[UserRepository](index.md)/[findAllByUserTags](find-all-by-user-tags.md)

# findAllByUserTags

[jvm]\
abstract fun [findAllByUserTags](find-all-by-user-tags.md)(tag: [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.userModule.model/-user/index.md)&gt;

Retrieves all [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s with a given [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md) from the database.

#### Return

a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s having the required [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md)

## Parameters

jvm

| | |
|---|---|
| tag | the required [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md) |