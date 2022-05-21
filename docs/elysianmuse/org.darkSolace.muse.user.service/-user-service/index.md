//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserService](index.md)

# UserService

[jvm]\
@Service

class [UserService](index.md)(@Autowiredval userRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowireduserSettingsRepository: [UserSettingsRepository](../../org.darkSolace.muse.user.repository/-user-settings-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md), @AutowiredmailService: [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md))

Service class for [User](../../org.darkSolace.muse.user.model/-user/index.md) related tasks.

## See also

jvm

| | |
|---|---|
| [org.darkSolace.muse.user.service.UserRoleService](../-user-role-service/index.md) |  |
| [org.darkSolace.muse.user.service.UserTagService](../-user-tag-service/index.md) |  |

## Constructors

| | |
|---|---|
| [UserService](-user-service.md) | [jvm]<br>fun [UserService](-user-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowireduserSettingsRepository: [UserSettingsRepository](../../org.darkSolace.muse.user.repository/-user-settings-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md), @AutowiredmailService: [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [createUser](create-user.md) | [jvm]<br>fun [createUser](create-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Creates and persists a [User](../../org.darkSolace.muse.user.model/-user/index.md) in the database Password is hashed in the process |
| [createUserFromSignUpRequest](create-user-from-sign-up-request.md) | [jvm]<br>fun [createUserFromSignUpRequest](create-user-from-sign-up-request.md)(signUpRequest: [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Creates a user from a [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md) |
| [deleteUser](delete-user.md) | [jvm]<br>@Transactional<br>fun [deleteUser](delete-user.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))<br>@Transactional<br>fun [deleteUser](delete-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md))<br>Deletes a [User](../../org.darkSolace.muse.user.model/-user/index.md) from the database |
| [getAll](get-all.md) | [jvm]<br>fun [getAll](get-all.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;<br>Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of all [User](../../org.darkSolace.muse.user.model/-user/index.md)s |
| [getAllWithRole](get-all-with-role.md) | [jvm]<br>fun [getAllWithRole](get-all-with-role.md)(role: [Role](../../org.darkSolace.muse.user.model/-role/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;<br>Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of all [User](../../org.darkSolace.muse.user.model/-user/index.md)s with a given [Role](../../org.darkSolace.muse.user.model/-role/index.md) |
| [getById](get-by-id.md) | [jvm]<br>fun [getById](get-by-id.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Finds a [User](../../org.darkSolace.muse.user.model/-user/index.md) by its ID |
| [updateLastLogin](update-last-login.md) | [jvm]<br>fun [updateLastLogin](update-last-login.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md))<br>Updates the lastLogInDate timestamp of a given user. |
| [updateUserSettings](update-user-settings.md) | [jvm]<br>@Transactional<br>fun [updateUserSettings](update-user-settings.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), settings: [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Replaces the [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md) of a given [User](../../org.darkSolace.muse.user.model/-user/index.md) and persists it |

## Properties

| Name | Summary |
|---|---|
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md) |
