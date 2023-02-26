//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserController](index.md)

# UserController

[jvm]\
@RestController

@RequestMapping(value = [&quot;/api/user&quot;])

class [UserController](index.md)(@Autowiredval
userService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md), @Autowiredval
userRoleService: [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md), @Autowiredval
userTagService: [UserTagService](../../org.darkSolace.muse.user.service/-user-tag-service/index.md), @Autowiredval
suspensionService: [SuspensionService](../../org.darkSolace.muse.user.service/-suspension-service/index.md))

RestController defining endpoints regarding all user activity Subject to change

## Constructors

|                                       |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|---------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [UserController](-user-controller.md) | [jvm]<br>fun [UserController](-user-controller.md)(@AutowireduserService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md), @AutowireduserRoleService: [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md), @AutowireduserTagService: [UserTagService](../../org.darkSolace.muse.user.service/-user-tag-service/index.md), @AutowiredsuspensionService: [SuspensionService](../../org.darkSolace.muse.user.service/-suspension-service/index.md)) |

## Functions

| Name                                                       | Summary                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [addTagToUser](add-tag-to-user.md)                         | [jvm]<br>@PutMapping(value = [&quot;/{user}/tag/{tag}&quot;])<br>fun [addTagToUser](add-tag-to-user.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?, @PathVariabletag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Adds a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to a [User](../../org.darkSolace.muse.user.model/-user/index.md). A [User](../../org.darkSolace.muse.user.model/-user/index.md) can add [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)s to itself. To add tags to a different user either the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required.                             |
| [confirmSuspension](confirm-suspension.md)                 | [jvm]<br>@PostMapping(value = [&quot;/suspend/confirm/{confirmationCode}&quot;])<br>fun [confirmSuspension](confirm-suspension.md)(@PathVariableconfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Confirms a [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md), identified by its confirmation code.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| [deleteUser](delete-user.md)                               | [jvm]<br>@DeleteMapping(value = [&quot;/{user}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)<br>fun [deleteUser](delete-user.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?, authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Deletes a user identified by its id. Listens on /api/user/{id} for DELETE requests. You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) role to access this endpoint.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| [getAllCurrentlySuspended](get-all-currently-suspended.md) | [jvm]<br>@GetMapping(value = [&quot;/suspend/all&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('ADMINISTRATOR', 'MODERATOR')&quot;)<br>fun [getAllCurrentlySuspended](get-all-currently-suspended.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserIdNameDTO](../../org.darkSolace.muse.user.model.dto/-user-id-name-d-t-o/index.md)&gt;<br>Retrieves all currently suspended users You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) role to access this endpoint.                                                                                                                                                                                                                                                                                                                                                                                                            |
| [getAllUsers](get-all-users.md)                            | [jvm]<br>@GetMapping(value = [&quot;/all&quot;])<br>fun [getAllUsers](get-all-users.md)(authentication: Authentication?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserIdNameDTO](../../org.darkSolace.muse.user.model.dto/-user-id-name-d-t-o/index.md)&gt;<br>Retrieves all users. Listens on /api/user/all.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| [getSuspensionHistory](get-suspension-history.md)          | [jvm]<br>@GetMapping(value = [&quot;/suspend/history/{user}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('ADMINISTRATOR', 'MODERATOR')&quot;)<br>fun [getSuspensionHistory](get-suspension-history.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntryDTO](../../org.darkSolace.muse.user.model.dto/-suspension-history-entry-d-t-o/index.md)&gt;<br>Retrieves the suspension history for a given user, identified by its id. You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) role to access this endpoint.                                                                                                                                                                                                                                                                   |
| [getUserById](get-user-by-id.md)                           | [jvm]<br>@GetMapping(value = [&quot;/{id}&quot;])<br>fun [getUserById](get-user-by-id.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication: Authentication?): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Retrieves a user by its id. Listens on /api/user/{id}.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| [removeTagFromUser](remove-tag-from-user.md)               | [jvm]<br>@DeleteMapping(value = [&quot;/{user}/tag/{tag}&quot;])<br>fun [removeTagFromUser](remove-tag-from-user.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?, @PathVariabletag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Removes a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) from a [User](../../org.darkSolace.muse.user.model/-user/index.md). A [User](../../org.darkSolace.muse.user.model/-user/index.md) can remove [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)s from itself. To remove tags from a different user either the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required. |
| [suspendUser](suspend-user.md)                             | [jvm]<br>@PostMapping(value = [&quot;/suspend/{id}&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('ADMINISTRATOR', 'MODERATOR')&quot;)<br>fun [suspendUser](suspend-user.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Suspends a user identified by its id. Listens on /api/user/{id} for POST requests. You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) role to access this endpoint.                                                                                                                                                                                                                                                                                                                                                                                  |

## Properties

| Name                                       | Summary                                                                                                                                           |
|--------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| [suspensionService](suspension-service.md) | [jvm]<br>val [suspensionService](suspension-service.md): [SuspensionService](../../org.darkSolace.muse.user.service/-suspension-service/index.md) |
| [userRoleService](user-role-service.md)    | [jvm]<br>val [userRoleService](user-role-service.md): [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md)       |
| [userService](user-service.md)             | [jvm]<br>val [userService](user-service.md): [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md)                         |
| [userTagService](user-tag-service.md)      | [jvm]<br>val [userTagService](user-tag-service.md): [UserTagService](../../org.darkSolace.muse.user.service/-user-tag-service/index.md)           |