//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[AvatarService](index.md)

# AvatarService

[jvm]\
@Service

class [AvatarService](index.md)(@Autowiredval userRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @Autowiredval avatarRepository: [AvatarRepository](../../org.darkSolace.muse.user.repository/-avatar-repository/index.md))

## Constructors

| | |
|---|---|
| [AvatarService](-avatar-service.md) | [jvm]<br>fun [AvatarService](-avatar-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredavatarRepository: [AvatarRepository](../../org.darkSolace.muse.user.repository/-avatar-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [changeAvatar](change-avatar.md) | [jvm]<br>@Transactional<br>fun [changeAvatar](change-avatar.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), avatar: [Avatar](../../org.darkSolace.muse.user.model/-avatar/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Changes the [Avatar](../../org.darkSolace.muse.user.model/-avatar/index.md) for the specified [User](../../org.darkSolace.muse.user.model/-user/index.md) and persists it in the database. |

## Properties

| Name | Summary |
|---|---|
| [avatarRepository](avatar-repository.md) | [jvm]<br>val [avatarRepository](avatar-repository.md): [AvatarRepository](../../org.darkSolace.muse.user.repository/-avatar-repository/index.md) |
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md) |
