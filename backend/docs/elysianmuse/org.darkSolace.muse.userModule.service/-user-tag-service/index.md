//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserTagService](index.md)

# UserTagService

[jvm]\
@Service

class [UserTagService](index.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md))

## Functions

| Name | Summary |
|---|---|
| [addTagToUser](add-tag-to-user.md) | [jvm]<br>@Transactional<br>fun [addTagToUser](add-tag-to-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md), tag: [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)? |
| [removeTagFromUser](remove-tag-from-user.md) | [jvm]<br>@Transactional<br>fun [removeTagFromUser](remove-tag-from-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md), tag: [UserTag](../../org.darkSolace.muse.userModule.model/-user-tag/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)? |

## Properties

| Name | Summary |
|---|---|
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md) |
