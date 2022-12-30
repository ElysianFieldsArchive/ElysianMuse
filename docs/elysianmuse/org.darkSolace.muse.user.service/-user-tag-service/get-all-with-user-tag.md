//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserTagService](index.md)/[getAllWithUserTag](get-all-with-user-tag.md)

# getAllWithUserTag

[jvm]\
fun [getAllWithUserTag](get-all-with-user-tag.md)(tag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;

Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of all [User](../../org.darkSolace.muse.user.model/-user/index.md)s with a given [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)

#### Return

a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of all [User](../../org.darkSolace.muse.user.model/-user/index.md)s with the given [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)- might be empty if no [User](../../org.darkSolace.muse.user.model/-user/index.md)s exist with this [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)

#### Parameters

jvm

| | |
|---|---|
| tag | the [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to filter by |
