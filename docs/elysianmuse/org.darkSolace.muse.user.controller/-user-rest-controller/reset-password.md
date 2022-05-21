//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)/[resetPassword](reset-password.md)

# resetPassword

[jvm]\

@PostMapping(value = [&quot;/reset/{passwordConfirmationCode}&quot;])

fun [resetPassword](reset-password.md)(@PathVariablepasswordConfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @RequestBodynewPassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
