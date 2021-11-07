//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.controller](../index.md)/[UserRestController](index.md)

# UserRestController

[jvm]\
@RestController

@RequestMapping(value = ["/api/user"])

class [UserRestController](index.md)(@AutowireduserService: [UserService](../../org.darkSolace.muse.userModule.service/-user-service/index.md))

RestController defining endpoints regarding all user activity Subject to change

## Constructors

| | |
|---|---|
| [UserRestController](-user-rest-controller.md) | [jvm]<br>fun [UserRestController](-user-rest-controller.md)(@AutowireduserService: [UserService](../../org.darkSolace.muse.userModule.service/-user-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getAllUsers](get-all-users.md) | [jvm]<br>@GetMapping(value = ["/all"])<br>fun [getAllUsers](get-all-users.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.userModule.model/-user/index.md)&gt;<br>Retrieves all users. Listens on /api/user/all. |
| [getUserById](get-user-by-id.md) | [jvm]<br>@GetMapping(value = ["/{id}"])<br>fun [getUserById](get-user-by-id.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Retrieves a user by its id. Listens on /api/user/{id}. |

## Properties

| Name | Summary |
|---|---|
| [userService](user-service.md) | [jvm]<br>val [userService](user-service.md): [UserService](../../org.darkSolace.muse.userModule.service/-user-service/index.md) |
