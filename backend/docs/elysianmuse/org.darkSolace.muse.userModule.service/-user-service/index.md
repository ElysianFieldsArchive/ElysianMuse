//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserService](index.md)

# UserService

[jvm]\
@Service

class [UserService](index.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md), @AutowiredavatarRepository: [AvatarRepository](../../org.darkSolace.muse.userModule.repository/-avatar-repository/index.md), @AutowireduserSettingsRepository: [UserSettingsRepository](../../org.darkSolace.muse.userModule.repository/-user-settings-repository/index.md))

Service class for [User](../../org.darkSolace.muse.userModule.model/-user/index.md) related tasks.

## See also

jvm

| | |
|---|---|
| [org.darkSolace.muse.userModule.service.UserRoleService](../-user-role-service/index.md) |  |
| [org.darkSolace.muse.userModule.service.UserTagService](../-user-tag-service/index.md) |  |

## Constructors

| | |
|---|---|
| [UserService](-user-service.md) | [jvm]<br>fun [UserService](-user-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md), @AutowiredavatarRepository: [AvatarRepository](../../org.darkSolace.muse.userModule.repository/-avatar-repository/index.md), @AutowireduserSettingsRepository: [UserSettingsRepository](../../org.darkSolace.muse.userModule.repository/-user-settings-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [changeAvatar](change-avatar.md) | [jvm]<br>@Transactional<br>fun [changeAvatar](change-avatar.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md), avatar: [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Changes the [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md) for the specified user and persists it in the database. |
| [createUser](create-user.md) | [jvm]<br>fun [createUser](create-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md) |
| [deleteUser](delete-user.md) | [jvm]<br>@Transactional<br>fun [deleteUser](delete-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md))<br>Deletes a [User](../../org.darkSolace.muse.userModule.model/-user/index.md) from the database |
| [getAll](get-all.md) | [jvm]<br>fun [getAll](get-all.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.userModule.model/-user/index.md)&gt;<br>Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of all [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s |
| [getAllWithRole](get-all-with-role.md) | [jvm]<br>fun [getAllWithRole](get-all-with-role.md)(role: [Role](../../org.darkSolace.muse.userModule.model/-role/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.userModule.model/-user/index.md)&gt;<br>Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of all [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s with a given [Role](../../org.darkSolace.muse.userModule.model/-role/index.md) |
| [getAllWithUserTag](get-all-with-user-tag.md) | [jvm]<br>fun [getAllWithUserTag](get-all-with-user-tag.md)(tag: [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.userModule.model/-user/index.md)&gt;<br>Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of all [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s with a given [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md) |
| [getById](get-by-id.md) | [jvm]<br>@Transactional<br>fun [getById](get-by-id.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Finds a [User](../../org.darkSolace.muse.userModule.model/-user/index.md) by its ID |
| [updateUserSettings](update-user-settings.md) | [jvm]<br>@Transactional<br>fun [updateUserSettings](update-user-settings.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md), settings: [UserSettings](../../org.darkSolace.muse.userModule.model/-user-settings/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Replaces the [UserSettings](../../org.darkSolace.muse.userModule.model/-user-settings/index.md) of a given [User](../../org.darkSolace.muse.userModule.model/-user/index.md) and persists it |

## Properties

| Name | Summary |
|---|---|
| [avatarRepository](avatar-repository.md) | [jvm]<br>val [avatarRepository](avatar-repository.md): [AvatarRepository](../../org.darkSolace.muse.userModule.repository/-avatar-repository/index.md) |
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md) |
| [userSettingsRepository](user-settings-repository.md) | [jvm]<br>val [userSettingsRepository](user-settings-repository.md): [UserSettingsRepository](../../org.darkSolace.muse.userModule.repository/-user-settings-repository/index.md) |