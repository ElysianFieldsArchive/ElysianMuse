//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.controller](../index.md)
/[AuthController](index.md)/[authenticateUser](authenticate-user.md)

# authenticateUser

[jvm]\

@PostMapping(value = ["/signin"])

fun [authenticateUser](authenticate-user.md)(
@RequestBodyloginRequest: [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md)):
ResponseEntity&lt;*&gt;?

Checks a transmitted [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md) for a valid
username/password pair. Listens on /api/auth/signin.

#### Return

a [org.darkSolace.muse.securityModule.model.JwtResponse](../../org.darkSolace.muse.securityModule.model/-jwt-response/index.md)
containing a token or HTTP 401 is username or password are invalid

## Samples

## Parameters

jvm

| | |
|---|---|
| loginRequest | a [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md) containing username and password |
