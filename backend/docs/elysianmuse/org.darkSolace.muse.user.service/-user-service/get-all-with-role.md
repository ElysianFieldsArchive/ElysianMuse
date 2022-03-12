//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserService](index.md)
/[getAllWithRole](get-all-with-role.md)

# getAllWithRole

[jvm]\
fun [getAllWithRole](get-all-with-role.md)(
role: [Role](../../org.darkSolace.muse.user.model/-role/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](
../../org.darkSolace.muse.user.model/-user/index.md)&gt;

Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of
all [User](../../org.darkSolace.muse.user.model/-user/index.md)s with a
given [Role](../../org.darkSolace.muse.user.model/-role/index.md)

#### Return

a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of
all [User](../../org.darkSolace.muse.user.model/-user/index.md)s with the
given [Role](../../org.darkSolace.muse.user.model/-role/index.md)- might be empty if
no [User](../../org.darkSolace.muse.user.model/-user/index.md)s exist with
this [Role](../../org.darkSolace.muse.user.model/-role/index.md)

## Parameters

jvm

| | |
|---|---|
| role | the [Role](../../org.darkSolace.muse.user.model/-role/index.md) to filter by |
