//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserService](index.md)/[changeAvatar](change-avatar.md)

# changeAvatar

[jvm]\

@Transactional

fun [changeAvatar](change-avatar.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md), avatar: [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?

Changes the [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md) for the specified [User](../../org.darkSolace.muse.userModule.model/-user/index.md) and persists it in the database.

#### Return

the modified [User](../../org.darkSolace.muse.userModule.model/-user/index.md) or null if the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) was not found

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) to modify |
| avatar | the new [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md) |
