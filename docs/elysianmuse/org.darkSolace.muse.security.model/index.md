//[elysianmuse](../../index.md)/[org.darkSolace.muse.security.model](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [JwtResponse](-jwt-response/index.md) | [jvm]<br>class [JwtResponse](-jwt-response/index.md)(val token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>[JwtResponse](-jwt-response/index.md) class containing the token, user id, username and email. Used as after a successful login to transmit all required data. |
| [LoginRequest](-login-request/index.md) | [jvm]<br>data class [LoginRequest](-login-request/index.md)(val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>[LoginRequest](-login-request/index.md) model containing username and password used to sign in |
| [SignUpRequest](-sign-up-request/index.md) | [jvm]<br>data class [SignUpRequest](-sign-up-request/index.md)(val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>[SignUpRequest](-sign-up-request/index.md) model containing username, password and email to create a new user account |
| [SignUpResponse](-sign-up-response/index.md) | [jvm]<br>enum [SignUpResponse](-sign-up-response/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[SignUpResponse](-sign-up-response/index.md)&gt; <br>Enumeration containing all states of a [SignUpRequest](-sign-up-request/index.md) |
| [UserDetails](-user-details/index.md) | [jvm]<br>class [UserDetails](-user-details/index.md)(val user: [User](../org.darkSolace.muse.user.model/-user/index.md)?) : UserDetails<br>UserDetails model to pass required information to the [org.darkSolace.muse.security.service.AuthTokenFilter](../org.darkSolace.muse.security.service/-auth-token-filter/index.md) |
