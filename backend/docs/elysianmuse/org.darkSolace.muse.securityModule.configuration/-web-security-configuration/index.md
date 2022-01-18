//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.configuration](../index.md)
/[WebSecurityConfiguration](index.md)

# WebSecurityConfiguration

[jvm]\
@Configuration

@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)

class [WebSecurityConfiguration](index.md)(
@AutowireduserDetailsService: [UserDetailsService](../../org.darkSolace.muse.securityModule.service/-user-details-service/index.md)
,
@AutowiredunauthorizedHandler: [AuthEntryPointJwt](../../org.darkSolace.muse.securityModule.service/-auth-entry-point-jwt/index.md)) :
WebSecurityConfigurerAdapter

Configuration class to configure authentication via JWT

## Constructors

| | |
|---|---|
| [WebSecurityConfiguration](-web-security-configuration.md) | [jvm]<br>fun [WebSecurityConfiguration](-web-security-configuration.md)(@AutowireduserDetailsService: [UserDetailsService](../../org.darkSolace.muse.securityModule.service/-user-details-service/index.md), @AutowiredunauthorizedHandler: [AuthEntryPointJwt](../../org.darkSolace.muse.securityModule.service/-auth-entry-point-jwt/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [authenticationManagerBean](index.md#542724139%2FFunctions%2F-1216412040) | [jvm]<br>open fun [authenticationManagerBean](index.md#542724139%2FFunctions%2F-1216412040)(): AuthenticationManager |
| [configure](index.md#138919942%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [configure](index.md#138919942%2FFunctions%2F-1216412040)(web: WebSecurity) |
| [getApplicationContext](index.md#581339%2FFunctions%2F-1216412040) | [jvm]<br>fun [getApplicationContext](index.md#581339%2FFunctions%2F-1216412040)(): ApplicationContext |
| [getHttp](index.md#-1235949710%2FFunctions%2F-1216412040) | [jvm]<br>fun [getHttp](index.md#-1235949710%2FFunctions%2F-1216412040)(): HttpSecurity |
| [init](index.md#968965430%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [init](index.md#968965430%2FFunctions%2F-1216412040)(web: WebSecurity) |
| [passwordEncoder](password-encoder.md) | [jvm]<br>@Bean<br>fun [passwordEncoder](password-encoder.md)(): BCryptPasswordEncoder<br>Sets the password encoder to BCryptPasswordEncoder |
| [setApplicationContext](index.md#-1107405718%2FFunctions%2F-1216412040) | [jvm]<br>@Autowired<br>open fun [setApplicationContext](index.md#-1107405718%2FFunctions%2F-1216412040)(context: ApplicationContext) |
| [setAuthenticationConfiguration](index.md#528597738%2FFunctions%2F-1216412040) | [jvm]<br>@Autowired<br>open fun [setAuthenticationConfiguration](index.md#528597738%2FFunctions%2F-1216412040)(authenticationConfiguration: AuthenticationConfiguration) |
| [setContentNegotationStrategy](index.md#1189391010%2FFunctions%2F-1216412040) | [jvm]<br>@Autowired(required = false)<br>open fun [setContentNegotationStrategy](index.md#1189391010%2FFunctions%2F-1216412040)(contentNegotiationStrategy: ContentNegotiationStrategy) |
| [setObjectPostProcessor](index.md#-1081844195%2FFunctions%2F-1216412040) | [jvm]<br>@Autowired<br>open fun [setObjectPostProcessor](index.md#-1081844195%2FFunctions%2F-1216412040)(objectPostProcessor: ObjectPostProcessor&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;) |
| [setTrustResolver](index.md#1618322525%2FFunctions%2F-1216412040) | [jvm]<br>@Autowired(required = false)<br>open fun [setTrustResolver](index.md#1618322525%2FFunctions%2F-1216412040)(trustResolver: AuthenticationTrustResolver) |
| [userDetailsService](index.md#462167532%2FFunctions%2F-1216412040) | [jvm]<br>open fun [userDetailsService](index.md#462167532%2FFunctions%2F-1216412040)(): UserDetailsService |
| [userDetailsServiceBean](index.md#125784220%2FFunctions%2F-1216412040) | [jvm]<br>open fun [userDetailsServiceBean](index.md#125784220%2FFunctions%2F-1216412040)(): UserDetailsService |

## Properties

| Name | Summary |
|---|---|
| [authTokenFilter](auth-token-filter.md) | [jvm]<br>@Autowired<br>lateinit var [authTokenFilter](auth-token-filter.md): [AuthTokenFilter](../../org.darkSolace.muse.securityModule.service/-auth-token-filter/index.md) |
| [unauthorizedHandler](unauthorized-handler.md) | [jvm]<br>val [unauthorizedHandler](unauthorized-handler.md): [AuthEntryPointJwt](../../org.darkSolace.muse.securityModule.service/-auth-entry-point-jwt/index.md) |
| [userDetailsService](user-details-service.md) | [jvm]<br>val [userDetailsService](user-details-service.md): [UserDetailsService](../../org.darkSolace.muse.securityModule.service/-user-details-service/index.md) |
