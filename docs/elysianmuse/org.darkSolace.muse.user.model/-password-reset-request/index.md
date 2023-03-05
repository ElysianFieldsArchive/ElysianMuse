//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.model](../index.md)/[PasswordResetRequest](index.md)

# PasswordResetRequest

[jvm]\
@Entity

class [PasswordResetRequest](index.md)(val user: [User](../-user/index.md), val id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null)

Class holding a reset code to authorize the reset of a users password

## Constructors

| | |
|---|---|
| [PasswordResetRequest](-password-reset-request.md) | [jvm]<br>constructor(user: [User](../-user/index.md), id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [code](code.md) | [jvm]<br>val [code](code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>val [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [user](user.md) | [jvm]<br>val [user](user.md): [User](../-user/index.md) |
