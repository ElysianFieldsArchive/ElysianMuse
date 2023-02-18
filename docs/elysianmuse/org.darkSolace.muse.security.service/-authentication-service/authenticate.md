//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.service](../index.md)/[AuthenticationService](index.md)/[authenticate](authenticate.md)

# authenticate

[jvm]\
fun [authenticate](authenticate.md)(loginRequest: [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md)): [JwtResponse](../../org.darkSolace.muse.security.model/-jwt-response/index.md)?

Tries to authenticate a user.

TODO: Distinguish between wrong credentials and suspended users

#### Return

[JwtResponse](../../org.darkSolace.muse.security.model/-jwt-response/index.md) containing the token or null if authentication failed

## Parameters

jvm

| | |
|---|---|
| loginRequest | [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md) containing username and password |

## Throws

| | |
|---|---|
| org.springframework.security.authentication.BadCredentialsException |  |
| [org.darkSolace.muse.security.exception.EMailNotValidatedException](../../org.darkSolace.muse.security.exception/-e-mail-not-validated-exception/index.md) |  |
