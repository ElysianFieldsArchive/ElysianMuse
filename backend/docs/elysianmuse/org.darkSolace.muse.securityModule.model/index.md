//[elysianmuse](../../index.md)/[org.darkSolace.muse.securityModule.model](index.md)

# Package org.darkSolace.muse.securityModule.model

## Types

| Name | Summary |
|---|---|
| [JwtResponse](-jwt-response/index.md) | [jvm]<br>class [JwtResponse](-jwt-response/index.md)(token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [LoginRequest](-login-request/index.md) | [jvm]<br>data class [LoginRequest](-login-request/index.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [SignupRequest](-signup-request/index.md) | [jvm]<br>data class [SignupRequest](-signup-request/index.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [SignUpResponse](-sign-up-response/index.md) | [jvm]<br>enum [SignUpResponse](-sign-up-response/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[SignUpResponse](-sign-up-response/index.md)&gt; |
| [UserDetails](-user-details/index.md) | [jvm]<br>class [UserDetails](-user-details/index.md)(user: [User](../org.darkSolace.muse.userModule.model/-user/index.md)) : UserDetails |
