//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.controller](../index.md)/[AuthController](index.md)/[authenticateUser](authenticate-user.md)

# authenticateUser

[jvm]\

@PostMapping(value = ["/signin"])

fun [authenticateUser](authenticate-user.md)(@RequestBodyloginRequest: [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md)): ResponseEntity&lt;*&gt;?

Checks a transmitted [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md) for a valid username/password pair. Listens on /api/auth/signin.

#### Return

a [org.darkSolace.muse.security.model.JwtResponse](../../org.darkSolace.muse.security.model/-jwt-response/index.md) containing a token, HTTP 401 is username or password are invalid, or HTTP 301 is user is suspended

## Samples

## Parameters

jvm

| | |
|---|---|
| loginRequest | a [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md) containing username and password |
