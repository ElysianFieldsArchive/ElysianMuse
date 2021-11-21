//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.controller](../index.md)
/[AuthController](index.md)/[registerUser](register-user.md)

# registerUser

[jvm]\

@PostMapping(value = ["/signup"])

fun [registerUser](register-user.md)(
@RequestBodysignUpRequest: [SignUpRequest](../../org.darkSolace.muse.securityModule.model/-sign-up-request/index.md)):
ResponseEntity&lt;*&gt;

Checks a transmitted [SignUpRequest](../../org.darkSolace.muse.securityModule.model/-sign-up-request/index.md) and
creates a user if possible. Listens on /api/auth/signup.

#### Return

HTTP-Status 200 OK if user was created successfully or 400 BAD REQUEST if an error occurred

## Samples

## Parameters

jvm

| | |
|---|---|
| signUpRequest | a [SignUpRequest](../../org.darkSolace.muse.securityModule.model/-sign-up-request/index.md) containing username, password and email address |
