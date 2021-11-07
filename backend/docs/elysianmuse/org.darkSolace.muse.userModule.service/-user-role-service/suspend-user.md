//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserRoleService](index.md)/[suspendUser](suspend-user.md)

# suspendUser

[jvm]\

@Transactional

fun [suspendUser](suspend-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?

Suspends the provided user and persists it in the database

#### Return

the suspended [User](../../org.darkSolace.muse.userModule.model/-user/index.md) or null if the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) was not found

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) to be suspended |
