//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.controller](../index.md)/[AuthController](index.md)/[registerUser](register-user.md)

# registerUser

[jvm]\

@PostMapping(value = [&quot;/signup&quot;])

fun [registerUser](register-user.md)(@RequestBodysignUpRequest: [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md)): ResponseEntity&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Checks a transmitted [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md) and creates a user if possible. Listens on /api/auth/signup.

#### Return

HTTP-Status 200 OK if user was created successfully or 400 BAD REQUEST if an error occurred

#### Samples

#### Parameters

jvm

| | |
|---|---|
| signUpRequest | a [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md) containing username, password and email address |
