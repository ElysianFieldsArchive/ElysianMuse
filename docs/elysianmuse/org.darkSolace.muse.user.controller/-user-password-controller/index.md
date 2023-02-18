//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserPasswordController](index.md)

# UserPasswordController

[jvm]\
@RestController

@RequestMapping(value = [&quot;/api/user&quot;])

class [UserPasswordController](index.md)(@Autowiredval userService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md), @Autowiredval userPasswordService: [UserPasswordService](../../org.darkSolace.muse.user.service/-user-password-service/index.md))

RestController defining endpoints regarding all password reset activities

## Constructors

| | |
|---|---|
| [UserPasswordController](-user-password-controller.md) | [jvm]<br>fun [UserPasswordController](-user-password-controller.md)(@AutowireduserService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md), @AutowireduserPasswordService: [UserPasswordService](../../org.darkSolace.muse.user.service/-user-password-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [requestPasswordResetByEmailOrUsername](request-password-reset-by-email-or-username.md) | [jvm]<br>@PostMapping(value = [&quot;/reset&quot;])<br>fun [requestPasswordResetByEmailOrUsername](request-password-reset-by-email-or-username.md)(@RequestBodyidentifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Request a password reset for a user, identified either by email or username |
| [resetPassword](reset-password.md) | [jvm]<br>@PostMapping(value = [&quot;/reset/{passwordConfirmationCode}&quot;])<br>fun [resetPassword](reset-password.md)(@PathVariablepasswordConfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @RequestBodynewPassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Updates a users password if a valid [org.darkSolace.muse.user.model.PasswordResetRequest](../../org.darkSolace.muse.user.model/-password-reset-request/index.md) exists. |

## Properties

| Name | Summary |
|---|---|
| [userPasswordService](user-password-service.md) | [jvm]<br>val [userPasswordService](user-password-service.md): [UserPasswordService](../../org.darkSolace.muse.user.service/-user-password-service/index.md) |
| [userService](user-service.md) | [jvm]<br>val [userService](user-service.md): [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md) |
