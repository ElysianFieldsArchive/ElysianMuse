//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.service](../index.md)/[JwtUtils](index.md)

# JwtUtils

[jvm]\
@Component

class [JwtUtils](index.md)

Utility Component for tasks related to JWT

## Constructors

| | |
|---|---|
| [JwtUtils](-jwt-utils.md) | [jvm]<br>fun [JwtUtils](-jwt-utils.md)() |

## Functions

| Name | Summary |
|---|---|
| [generateJwtToken](generate-jwt-token.md) | [jvm]<br>fun [generateJwtToken](generate-jwt-token.md)(authentication: Authentication): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Generates a signed JWT token |
| [getUserNameFromJwtToken](get-user-name-from-jwt-token.md) | [jvm]<br>fun [getUserNameFromJwtToken](get-user-name-from-jwt-token.md)(token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Extracts a username from a JWT token |
| [parseJwt](parse-jwt.md) | [jvm]<br>fun [parseJwt](parse-jwt.md)(request: HttpServletRequest): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Extracts the JWT from a HttpServletRequest |
| [validateJwtToken](validate-jwt-token.md) | [jvm]<br>fun [validateJwtToken](validate-jwt-token.md)(authToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Validates a token for validity |
