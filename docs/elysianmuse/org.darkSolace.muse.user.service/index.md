//[elysianmuse](../../index.md)/[org.darkSolace.muse.user.service](index.md)

# Package org.darkSolace.muse.user.service

## Types

| Name | Summary |
|---|---|
| [AvatarService](-avatar-service/index.md) | [jvm]<br>@Service<br>class [AvatarService](-avatar-service/index.md)(@AutowireduserRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredavatarRepository: [AvatarRepository](../org.darkSolace.muse.user.repository/-avatar-repository/index.md)) |
| [SuspenstionService](-suspenstion-service/index.md) | [jvm]<br>@Service<br>class [SuspenstionService](-suspenstion-service/index.md)(@AutowireduserRoleService: [UserRoleService](-user-role-service/index.md), @AutowireduserRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredsuspensionHistoryRepository: [SuspensionHistoryRepository](../org.darkSolace.muse.user.repository/-suspension-history-repository/index.md)) |
| [UserRoleService](-user-role-service/index.md) | [jvm]<br>@Service<br>class [UserRoleService](-user-role-service/index.md)(@AutowireduserRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredsuspensionHistoryRepository: [SuspensionHistoryRepository](../org.darkSolace.muse.user.repository/-suspension-history-repository/index.md))<br>Service class for [Role](../org.darkSolace.muse.user.model/-role/index.md) related tasks when working with [User](../org.darkSolace.muse.user.model/-user/index.md)s. |
| [UserService](-user-service/index.md) | [jvm]<br>@Service<br>class [UserService](-user-service/index.md)(@AutowireduserRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowireduserSettingsRepository: [UserSettingsRepository](../org.darkSolace.muse.user.repository/-user-settings-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md))<br>Service class for [User](../org.darkSolace.muse.user.model/-user/index.md) related tasks. |
| [UserTagService](-user-tag-service/index.md) | [jvm]<br>@Service<br>class [UserTagService](-user-tag-service/index.md)(@AutowireduserRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md))<br>Service class for [UserTag](../org.darkSolace.muse.user.model/-user-tag/index.md) related tasks when working with [User](../org.darkSolace.muse.user.model/-user/index.md)s. |
