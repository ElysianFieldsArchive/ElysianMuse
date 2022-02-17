//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.controller](../index.md)/[AuthController](index.md)

# AuthController

[jvm]\
@RestController

@RequestMapping(value = ["api/auth"])

class [AuthController](index.md)(@AutowiredauthenticationService: [AuthenticationService](../../org.darkSolace.muse.security.service/-authentication-service/index.md), @AutowireduserRoleService: [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md))

## Functions

| Name | Summary |
|---|---|
| [authenticateUser](authenticate-user.md) | [jvm]<br>@PostMapping(value = ["/signin"])<br>fun [authenticateUser](authenticate-user.md)(@RequestBodyloginRequest: [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md)): ResponseEntity&lt;*&gt;?<br>Checks a transmitted [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md) for a valid username/password pair. Listens on /api/auth/signin. |
| [registerUser](register-user.md) | [jvm]<br>@PostMapping(value = ["/signup"])<br>fun [registerUser](register-user.md)(@RequestBodysignUpRequest: [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md)): ResponseEntity&lt;*&gt;<br>Checks a transmitted [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md) and creates a user if possible. Listens on /api/auth/signup. |

## Properties

| Name | Summary |
|---|---|
| [authenticationService](authentication-service.md) | [jvm]<br>val [authenticationService](authentication-service.md): [AuthenticationService](../../org.darkSolace.muse.security.service/-authentication-service/index.md) |
| [userRoleService](user-role-service.md) | [jvm]<br>val [userRoleService](user-role-service.md): [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md) |
