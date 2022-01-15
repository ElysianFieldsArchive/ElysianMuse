//[elysianmuse](../../index.md)/[org.darkSolace.muse.securityModule.model](index.md)

# Package org.darkSolace.muse.securityModule.model

## Types

| Name | Summary |
|---|---|
| [JwtResponse](-jwt-response/index.md) | [jvm]<br>class [JwtResponse](-jwt-response/index.md)(token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>[JwtResponse](-jwt-response/index.md) class containing the token, user id, username and email. Used as after a successful login to transmit all required data. |
| [LoginRequest](-login-request/index.md) | [jvm]<br>data class [LoginRequest](-login-request/index.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>[LoginRequest](-login-request/index.md) model containing username and password used to sign in |
| [SignUpRequest](-sign-up-request/index.md) | [jvm]<br>data class [SignUpRequest](-sign-up-request/index.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>[SignUpRequest](-sign-up-request/index.md) model containing username, password and email to create a new user account |
| [SignUpResponse](-sign-up-response/index.md) | [jvm]<br>enum [SignUpResponse](-sign-up-response/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[SignUpResponse](-sign-up-response/index.md)&gt; <br>Enumeration containing all states of a [SignUpRequest](-sign-up-request/index.md) |
| [UserDetails](-user-details/index.md) | [jvm]<br>class [UserDetails](-user-details/index.md)(user: [User](../org.darkSolace.muse.userModule.model/-user/index.md)) : UserDetails<br>UserDetails model to pass required information to the [org.darkSolace.muse.securityModule.service.AuthTokenFilter](../org.darkSolace.muse.securityModule.service/-auth-token-filter/index.md) |
