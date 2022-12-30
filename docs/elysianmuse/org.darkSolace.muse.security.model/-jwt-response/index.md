//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.model](../index.md)/[JwtResponse](index.md)

# JwtResponse

[jvm]\
class [JwtResponse](index.md)(val token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

[JwtResponse](index.md) class containing the token, user id, username and email. Used as after a successful login to transmit all required data.

#### See also

jvm

|                                                                                                                        |
|------------------------------------------------------------------------------------------------------------------------|
| [AuthController.authenticateUser](../../org.darkSolace.muse.security.controller/-auth-controller/authenticate-user.md) |

## Constructors

| | |
|---|---|
| [JwtResponse](-jwt-response.md) | [jvm]<br>fun [JwtResponse](-jwt-response.md)(token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [id](id.md) | [jvm]<br>val [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [role](role.md) | [jvm]<br>val [role](role.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [token](token.md) | [jvm]<br>val [token](token.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [username](username.md) | [jvm]<br>val [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
