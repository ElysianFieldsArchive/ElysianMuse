//[elysianmuse](../../index.md)/[org.darkSolace.muse.configuration](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AuthenticationConfiguration](-authentication-configuration/index.md) | [jvm]<br>@Configuration<br>class [AuthenticationConfiguration](-authentication-configuration/index.md)(@AutowireduserDetailsService: [UserDetailsService](../org.darkSolace.muse.security.service/-user-details-service/index.md), @AutowiredpasswordEncoder: BCryptPasswordEncoder) |
| [WebSecurityConfiguration](-web-security-configuration/index.md) | [jvm]<br>@Configuration<br>@EnableWebSecurity<br>@EnableMethodSecurity(prePostEnabled = true)<br>class [WebSecurityConfiguration](-web-security-configuration/index.md)(@Autowiredval unauthorizedHandler: [AuthEntryPointJwt](../org.darkSolace.muse.security.service/-auth-entry-point-jwt/index.md), @Autowiredval authTokenFilter: [AuthTokenFilter](../org.darkSolace.muse.security.service/-auth-token-filter/index.md), @Autowiredval lastSeenFilter: [LastSeenFilter](../org.darkSolace.muse.lastSeen.service/-last-seen-filter/index.md))<br>Configuration class to configure authentication via JWT |
