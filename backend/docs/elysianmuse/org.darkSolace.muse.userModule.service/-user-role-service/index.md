//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserRoleService](index.md)

# UserRoleService

[jvm]\
@Service

class [UserRoleService](index.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md))

Service class for [Role](../../org.darkSolace.muse.userModule.model/-role/index.md) related tasks when working with [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s.

## See also

jvm

| | |
|---|---|
| [org.darkSolace.muse.userModule.service.UserService](../-user-service/index.md) |  |

## Constructors

| | |
|---|---|
| [UserRoleService](-user-role-service.md) | [jvm]<br>fun [UserRoleService](-user-role-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [changeRole](change-role.md) | [jvm]<br>@Transactional<br>fun [changeRole](change-role.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md), role: [Role](../../org.darkSolace.muse.userModule.model/-role/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Changes the role of a [User](../../org.darkSolace.muse.userModule.model/-user/index.md) and persists it in the database |
| [suspendUser](suspend-user.md) | [jvm]<br>@Transactional<br>fun [suspendUser](suspend-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Suspends the provided [User](../../org.darkSolace.muse.userModule.model/-user/index.md) and persists it in the database |

## Properties

| Name | Summary |
|---|---|
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md) |
