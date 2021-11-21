//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.service](../index.md)
/[AuthenticationService](index.md)

# AuthenticationService

[jvm]\
@Service

class [AuthenticationService](index.md)(@AutowiredauthenticationManager: AuthenticationManager,
@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md),
@AutowireduserService: [UserService](../../org.darkSolace.muse.userModule.service/-user-service/index.md),
@AutowiredjwtUtils: [JwtUtils](../-jwt-utils/index.md))

Service to handle everything in regard to authentication.

## Constructors

| | |
|---|---|
| [AuthenticationService](-authentication-service.md) | [jvm]<br>fun [AuthenticationService](-authentication-service.md)(@AutowiredauthenticationManager: AuthenticationManager, @AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md), @AutowireduserService: [UserService](../../org.darkSolace.muse.userModule.service/-user-service/index.md), @AutowiredjwtUtils: [JwtUtils](../-jwt-utils/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [authenticate](authenticate.md) | [jvm]<br>fun [authenticate](authenticate.md)(loginRequest: [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md)): [JwtResponse](../../org.darkSolace.muse.securityModule.model/-jwt-response/index.md)?<br>Tries to authenticate a user. |
| [signUpUser](sign-up-user.md) | [jvm]<br>fun [signUpUser](sign-up-user.md)(signUpRequest: [SignUpRequest](../../org.darkSolace.muse.securityModule.model/-sign-up-request/index.md)): [SignUpResponse](../../org.darkSolace.muse.securityModule.model/-sign-up-response/index.md)<br>Creates a user with information provided via a [SignUpRequest](../../org.darkSolace.muse.securityModule.model/-sign-up-request/index.md) |

## Properties

| Name | Summary |
|---|---|
| [authenticationManager](authentication-manager.md) | [jvm]<br>val [authenticationManager](authentication-manager.md): AuthenticationManager |
| [jwtUtils](jwt-utils.md) | [jvm]<br>val [jwtUtils](jwt-utils.md): [JwtUtils](../-jwt-utils/index.md) |
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md) |
| [userService](user-service.md) | [jvm]<br>val [userService](user-service.md): [UserService](../../org.darkSolace.muse.userModule.service/-user-service/index.md) |
