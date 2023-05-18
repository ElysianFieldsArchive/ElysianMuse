//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.model](../index.md)/[SignUpRequest](index.md)

# SignUpRequest

class [SignUpRequest](index.md)(val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

[SignUpRequest](index.md) model containing username, password and email to create a new user account

#### See also

| |
|---|
| [UserService.createUser](../../org.darkSolace.muse.user.service/-user-service/create-user.md) |

## Constructors

| | |
|---|---|
| [SignUpRequest](-sign-up-request.md) | [jvm]<br>constructor(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [email](email.md) | [jvm]<br>@Email<br>val [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [password](password.md) | [jvm]<br>@Size(min = 6)<br>val [password](password.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [username](username.md) | [jvm]<br>@Size(min = 3)<br>val [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
