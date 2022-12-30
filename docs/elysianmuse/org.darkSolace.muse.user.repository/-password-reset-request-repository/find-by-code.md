//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.repository](../index.md)/[PasswordResetRequestRepository](index.md)/[findByCode](find-by-code.md)

# findByCode

[jvm]\
abstract fun [findByCode](find-by-code.md)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [PasswordResetRequest](../../org.darkSolace.muse.user.model/-password-reset-request/index.md)?

Retrieves a [PasswordResetRequest](../../org.darkSolace.muse.user.model/-password-reset-request/index.md) identified by the reset code

#### Return

the [PasswordResetRequest](../../org.darkSolace.muse.user.model/-password-reset-request/index.md) or `null`

#### Parameters

jvm

| | |
|---|---|
| code | the reset code |
