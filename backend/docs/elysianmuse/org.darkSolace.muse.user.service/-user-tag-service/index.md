//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserTagService](index.md)

# UserTagService

[jvm]\
@Service

class [UserTagService](index.md)(
@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md))

Service class for [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) related tasks when working
with [User](../../org.darkSolace.muse.user.model/-user/index.md)s.

## See also

jvm

| | |
|---|---|
| [org.darkSolace.muse.user.service.UserService](../-user-service/index.md) |  |

## Constructors

| | |
|---|---|
| [UserTagService](-user-tag-service.md) | [jvm]<br>fun [UserTagService](-user-tag-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addTagToUser](add-tag-to-user.md) | [jvm]<br>@Transactional<br>fun [addTagToUser](add-tag-to-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), tag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Adds a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to a [User](../../org.darkSolace.muse.user.model/-user/index.md) |
| [removeTagFromUser](remove-tag-from-user.md) | [jvm]<br>@Transactional<br>fun [removeTagFromUser](remove-tag-from-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), tag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Removes a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to a [User](../../org.darkSolace.muse.user.model/-user/index.md) |

## Properties

| Name | Summary |
|---|---|
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md) |
