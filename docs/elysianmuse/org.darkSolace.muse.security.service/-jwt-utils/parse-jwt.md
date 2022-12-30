//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.service](../index.md)/[JwtUtils](index.md)/[parseJwt](parse-jwt.md)

# parseJwt

[jvm]\
fun [parseJwt](parse-jwt.md)(request: HttpServletRequest): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?

Extracts the JWT from a HttpServletRequest

#### Return

the extracted JWT or `null` if authentication is missing

#### Parameters

jvm

| | |
|---|---|
| request | the HttpServletRequest containing the authentication as a &quot;Bearer&quot;-Header |
