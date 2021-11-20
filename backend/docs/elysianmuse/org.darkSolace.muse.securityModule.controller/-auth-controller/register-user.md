//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.controller](../index.md)
/[AuthController](index.md)/[registerUser](register-user.md)

# registerUser

[jvm]\

@PostMapping(value = ["/signup"])

fun [registerUser](register-user.md)(
@RequestBodysignUpRequest: [SignupRequest](../../org.darkSolace.muse.securityModule.model/-signup-request/index.md)):
ResponseEntity&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;
