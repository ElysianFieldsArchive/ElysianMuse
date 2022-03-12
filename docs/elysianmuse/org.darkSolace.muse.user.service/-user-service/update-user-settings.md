//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserService](index.md)/[updateUserSettings](update-user-settings.md)

# updateUserSettings

[jvm]\

@Transactional

fun [updateUserSettings](update-user-settings.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), settings: [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?

Replaces the [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md) of a given [User](../../org.darkSolace.muse.user.model/-user/index.md) and persists it

#### Return

the modified [User](../../org.darkSolace.muse.user.model/-user/index.md) or null if the [User](../../org.darkSolace.muse.user.model/-user/index.md) does not exist

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.user.model/-user/index.md) to be modified |
| settings | the new [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md) to be applied |
