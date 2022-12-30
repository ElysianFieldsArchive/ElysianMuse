//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserPasswordController](index.md)/[requestPasswordResetByEmailOrUsername](request-password-reset-by-email-or-username.md)

# requestPasswordResetByEmailOrUsername

[jvm]\

@PostMapping(value = [&quot;/reset&quot;])

fun [requestPasswordResetByEmailOrUsername](request-password-reset-by-email-or-username.md)(@RequestBodyidentifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Request a password reset for a user, identified either by email or username

#### Return

HTTP 200 (OK) on success, 400 (BAD REQUEST) otherwise

#### Parameters

jvm

| | |
|---|---|
| identifier | the identifying user information - either username or email |
