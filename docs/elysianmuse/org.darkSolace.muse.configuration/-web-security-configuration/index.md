//[elysianmuse](../../../index.md)/[org.darkSolace.muse.configuration](../index.md)/[WebSecurityConfiguration](index.md)

# WebSecurityConfiguration

[jvm]\
@Configuration

@EnableWebSecurity

@EnableMethodSecurity(prePostEnabled = true)

class [WebSecurityConfiguration](index.md)(@Autowiredval
unauthorizedHandler: [AuthEntryPointJwt](../../org.darkSolace.muse.security.service/-auth-entry-point-jwt/index.md),
@Autowiredval
authTokenFilter: [AuthTokenFilter](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md),
@Autowiredval lastSeenFilter: [LastSeenFilter](../../org.darkSolace.muse.lastSeen.service/-last-seen-filter/index.md))

Configuration class to configure authentication via JWT

## Constructors

| | |
|---|---|
| [WebSecurityConfiguration](-web-security-configuration.md) | [jvm]<br>fun [WebSecurityConfiguration](-web-security-configuration.md)(@AutowiredunauthorizedHandler: [AuthEntryPointJwt](../../org.darkSolace.muse.security.service/-auth-entry-point-jwt/index.md), @AutowiredauthTokenFilter: [AuthTokenFilter](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md), @AutowiredlastSeenFilter: [LastSeenFilter](../../org.darkSolace.muse.lastSeen.service/-last-seen-filter/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [filterChain](filter-chain.md) | [jvm]<br>@Bean<br>fun [filterChain](filter-chain.md)(http: HttpSecurity): SecurityFilterChain |

## Properties

| Name | Summary |
|---|---|
| [authTokenFilter](auth-token-filter.md) | [jvm]<br>val [authTokenFilter](auth-token-filter.md): [AuthTokenFilter](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md) |
| [lastSeenFilter](last-seen-filter.md) | [jvm]<br>val [lastSeenFilter](last-seen-filter.md): [LastSeenFilter](../../org.darkSolace.muse.lastSeen.service/-last-seen-filter/index.md) |
| [unauthorizedHandler](unauthorized-handler.md) | [jvm]<br>val [unauthorizedHandler](unauthorized-handler.md): [AuthEntryPointJwt](../../org.darkSolace.muse.security.service/-auth-entry-point-jwt/index.md) |
