//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.service](../index.md)/[AuthTokenFilter](index.md)

# AuthTokenFilter

[jvm]\
@Component

class [AuthTokenFilter](index.md) : OncePerRequestFilter

Filter to check the provided authorization for validity

#### See also

jvm

| |
|---|
| OncePerRequestFilter |

## Constructors

| | |
|---|---|
| [AuthTokenFilter](-auth-token-filter.md) | [jvm]<br>fun [AuthTokenFilter](-auth-token-filter.md)() |

## Functions

| Name | Summary |
|---|---|
| [afterPropertiesSet](index.md#2115246148%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [afterPropertiesSet](index.md#2115246148%2FFunctions%2F-1216412040)() |
| [destroy](index.md#-1289270679%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [destroy](index.md#-1289270679%2FFunctions%2F-1216412040)() |
| [doFilter](index.md#-1038434982%2FFunctions%2F-1216412040) | [jvm]<br>override fun [doFilter](index.md#-1038434982%2FFunctions%2F-1216412040)(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) |
| [init](index.md#-1306378036%2FFunctions%2F-1216412040) | [jvm]<br>override fun [init](index.md#-1306378036%2FFunctions%2F-1216412040)(filterConfig: FilterConfig) |
| [setBeanName](index.md#719905502%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setBeanName](index.md#719905502%2FFunctions%2F-1216412040)(beanName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [environment](index.md#179565424%2FProperties%2F-1216412040) | [jvm]<br>@Nullable<br>var [environment](index.md#179565424%2FProperties%2F-1216412040): Environment |
| [filterConfig](index.md#653156989%2FProperties%2F-1216412040) | [jvm]<br>@Nullable<br>@get:Nullable<br>val [filterConfig](index.md#653156989%2FProperties%2F-1216412040): FilterConfig |
| [jwtUtils](jwt-utils.md) | [jvm]<br>@Autowired<br>lateinit var [jwtUtils](jwt-utils.md): [JwtUtils](../-jwt-utils/index.md) |
| [servletContext](index.md#1632496429%2FProperties%2F-1216412040) | [jvm]<br>@Nullable<br>protected var [servletContext](index.md#1632496429%2FProperties%2F-1216412040): ServletContext |
| [userDetailsService](user-details-service.md) | [jvm]<br>@Autowired<br>lateinit var [userDetailsService](user-details-service.md): [UserDetailsService](../-user-details-service/index.md) |
