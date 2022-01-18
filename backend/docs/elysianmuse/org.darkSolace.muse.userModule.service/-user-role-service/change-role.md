//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserRoleService](index.md)/[changeRole](change-role.md)

# changeRole

[jvm]\

@Transactional

fun [changeRole](change-role.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md),
role: [Role](../../org.darkSolace.muse.userModule.model/-role/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)
?

Changes the role of a [User](../../org.darkSolace.muse.userModule.model/-user/index.md) and persists it in the database.

#### Return

the modified [User](../../org.darkSolace.muse.userModule.model/-user/index.md) or null if the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) was not found

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) to modify |
| role | the new [Role](../../org.darkSolace.muse.userModule.model/-role/index.md) of this [User](../../org.darkSolace.muse.userModule.model/-user/index.md) |
