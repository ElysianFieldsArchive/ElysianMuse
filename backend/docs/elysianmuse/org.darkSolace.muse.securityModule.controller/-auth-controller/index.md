//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.controller](../index.md)
/[AuthController](index.md)

# AuthController

[jvm]\
@RestController

@RequestMapping(value = ["api/auth"])

class [AuthController](index.md)

## Functions

| Name | Summary |
|---|---|
| [authenticateUser](authenticate-user.md) | [jvm]<br>@PostMapping(value = ["/signin"])<br>fun [authenticateUser](authenticate-user.md)(@RequestBodyloginRequest: [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md)): ResponseEntity&lt;*&gt;? |
| [registerUser](register-user.md) | [jvm]<br>@PostMapping(value = ["/signup"])<br>fun [registerUser](register-user.md)(@RequestBodysignUpRequest: [SignupRequest](../../org.darkSolace.muse.securityModule.model/-signup-request/index.md)): ResponseEntity&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Properties

| Name | Summary |
|---|---|
| [authenticationService](authentication-service.md) | [jvm]<br>@Autowired<br>lateinit var [authenticationService](authentication-service.md): [AuthenticationService](../../org.darkSolace.muse.securityModule.service/-authentication-service/index.md) |
