//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.controller](../index.md)
/[AuthController](index.md)/[authenticateUser](authenticate-user.md)

# authenticateUser

[jvm]\

@PostMapping(value = ["/signin"])

fun [authenticateUser](authenticate-user.md)(
@RequestBodyloginRequest: [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md)):
ResponseEntity&lt;*&gt;?
