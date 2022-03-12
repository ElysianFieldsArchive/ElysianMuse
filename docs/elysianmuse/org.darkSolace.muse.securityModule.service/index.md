//[elysianmuse](../../index.md)/[org.darkSolace.muse.securityModule.service](index.md)

# Package org.darkSolace.muse.securityModule.service

## Types

| Name | Summary |
|---|---|
| [AuthenticationService](-authentication-service/index.md) | [jvm]<br>@Service<br>class [AuthenticationService](-authentication-service/index.md)(@AutowiredauthenticationManager: AuthenticationManager, @AutowireduserRepository: [UserRepository](../org.darkSolace.muse.userModule.repository/-user-repository/index.md), @AutowireduserService: [UserService](../org.darkSolace.muse.userModule.service/-user-service/index.md), @AutowiredjwtUtils: [JwtUtils](-jwt-utils/index.md))<br>Service to handle everything in regard to authentication. |
| [AuthEntryPointJwt](-auth-entry-point-jwt/index.md) | [jvm]<br>@Component<br>class [AuthEntryPointJwt](-auth-entry-point-jwt/index.md) : AuthenticationEntryPoint<br>Component to handle unauthorised access to an endpoint |
| [AuthTokenFilter](-auth-token-filter/index.md) | [jvm]<br>@Component<br>class [AuthTokenFilter](-auth-token-filter/index.md) : OncePerRequestFilter<br>Filter to check the provided authorization for validity |
| [JwtUtils](-jwt-utils/index.md) | [jvm]<br>@Component<br>class [JwtUtils](-jwt-utils/index.md)<br>Utility Component for tasks related to JWT |
| [UserDetailsService](-user-details-service/index.md) | [jvm]<br>@Service<br>class [UserDetailsService](-user-details-service/index.md)(@AutowireduserRepository: [UserRepository](../org.darkSolace.muse.userModule.repository/-user-repository/index.md)) : UserDetailsService<br>Service to handle everything in regard to UserDetails. |
