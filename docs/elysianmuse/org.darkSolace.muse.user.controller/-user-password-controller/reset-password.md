//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserPasswordController](index.md)/[resetPassword](reset-password.md)

# resetPassword

[jvm]\

@PostMapping(value = [&quot;/reset/{passwordConfirmationCode}&quot;])

fun [resetPassword](reset-password.md)(@PathVariablepasswordConfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @RequestBodynewPassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Updates a users password if a valid [org.darkSolace.muse.user.model.PasswordResetRequest](../../org.darkSolace.muse.user.model/-password-reset-request/index.md) exists.

#### Return

HTTP 200 (OK) on success, 400 (BAD REQUEST) otherwise

#### Samples

#### Parameters

jvm

| | |
|---|---|
| passwordConfirmationCode | confirmation code to verify that the reset request is valid |
| newPassword | new password to be set, sent as the request body |
