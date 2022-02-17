//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.service](../index.md)/[AuthenticationService](index.md)/[authenticate](authenticate.md)

# authenticate

[jvm]\
fun [authenticate](authenticate.md)(loginRequest: [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md)): [JwtResponse](../../org.darkSolace.muse.securityModule.model/-jwt-response/index.md)?

Tries to authenticate a user.

TODO: Distinguish between wrong credentials and suspended users

#### Return

[JwtResponse](../../org.darkSolace.muse.securityModule.model/-jwt-response/index.md) containing the token or null if authentication failed

## Parameters

jvm

| | |
|---|---|
| loginRequest | [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md) containing username and password |
