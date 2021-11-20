//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.service](../index.md)
/[AuthenticationService](index.md)

# AuthenticationService

[jvm]\
@Service

class [AuthenticationService](index.md)

## Functions

| Name | Summary |
|---|---|
| [authenticate](authenticate.md) | [jvm]<br>fun [authenticate](authenticate.md)(loginRequest: [LoginRequest](../../org.darkSolace.muse.securityModule.model/-login-request/index.md)): [JwtResponse](../../org.darkSolace.muse.securityModule.model/-jwt-response/index.md)? |
| [signUpUser](sign-up-user.md) | [jvm]<br>fun [signUpUser](sign-up-user.md)(signUpRequest: [SignupRequest](../../org.darkSolace.muse.securityModule.model/-signup-request/index.md)): [SignUpResponse](../../org.darkSolace.muse.securityModule.model/-sign-up-response/index.md) |

## Properties

| Name | Summary |
|---|---|
| [authenticationManager](authentication-manager.md) | [jvm]<br>@Autowired<br>lateinit var [authenticationManager](authentication-manager.md): AuthenticationManager |
| [jwtUtils](jwt-utils.md) | [jvm]<br>@Autowired<br>lateinit var [jwtUtils](jwt-utils.md): [JwtUtils](../-jwt-utils/index.md) |
| [userRepository](user-repository.md) | [jvm]<br>@Autowired<br>lateinit var [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md) |
| [userService](user-service.md) | [jvm]<br>@Autowired<br>lateinit var [userService](user-service.md): [UserService](../../org.darkSolace.muse.userModule.service/-user-service/index.md) |
