//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserTagService](index.md)/[removeTagFromUser](remove-tag-from-user.md)

# removeTagFromUser

[jvm]\

@Transactional

fun [removeTagFromUser](remove-tag-from-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), tag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?

Removes a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to a [User](../../org.darkSolace.muse.user.model/-user/index.md)

#### Return

the modified [User](../../org.darkSolace.muse.user.model/-user/index.md) or `null`
if [User](../../org.darkSolace.muse.user.model/-user/index.md) does not exist

#### Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.user.model/-user/index.md) to be modified |
| tag | the [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to be removed |
