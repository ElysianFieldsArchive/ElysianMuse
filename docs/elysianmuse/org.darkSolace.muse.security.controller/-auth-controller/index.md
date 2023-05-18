//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.controller](../index.md)/[AuthController](index.md)

# AuthController

[jvm]\
@RestController

@RequestMapping(value = [&quot;api/auth&quot;])

@Validated

class [AuthController](index.md)(@Autowiredval authenticationService: [AuthenticationService](../../org.darkSolace.muse.security.service/-authentication-service/index.md), @Autowiredval userRoleService: [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md), @Autowiredval suspensionService: [SuspensionService](../../org.darkSolace.muse.user.service/-suspension-service/index.md))

## Constructors

| | |
|---|---|
| [AuthController](-auth-controller.md) | [jvm]<br>constructor(@AutowiredauthenticationService: [AuthenticationService](../../org.darkSolace.muse.security.service/-authentication-service/index.md), @AutowireduserRoleService: [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md), @AutowiredsuspensionService: [SuspensionService](../../org.darkSolace.muse.user.service/-suspension-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [authenticateUser](authenticate-user.md) | [jvm]<br>@PostMapping(value = [&quot;/signin&quot;])<br>fun [authenticateUser](authenticate-user.md)(@RequestBody@ValidloginRequest: [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md)): ResponseEntity&lt;*&gt;<br>Checks a transmitted [LoginRequest](../../org.darkSolace.muse.security.model/-login-request/index.md) for a valid username/password pair. Listens on /api/auth/signin. |
| [registerUser](register-user.md) | [jvm]<br>@PostMapping(value = [&quot;/signup&quot;])<br>fun [registerUser](register-user.md)(@Valid@RequestBodysignUpRequest: [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md)): ResponseEntity&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Checks a transmitted [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md) and creates a user if possible. Listens on /api/auth/signup. |

## Properties

| Name | Summary |
|---|---|
| [authenticationService](authentication-service.md) | [jvm]<br>val [authenticationService](authentication-service.md): [AuthenticationService](../../org.darkSolace.muse.security.service/-authentication-service/index.md) |
| [suspensionService](suspension-service.md) | [jvm]<br>val [suspensionService](suspension-service.md): [SuspensionService](../../org.darkSolace.muse.user.service/-suspension-service/index.md) |
| [userRoleService](user-role-service.md) | [jvm]<br>val [userRoleService](user-role-service.md): [UserRoleService](../../org.darkSolace.muse.user.service/-user-role-service/index.md) |
