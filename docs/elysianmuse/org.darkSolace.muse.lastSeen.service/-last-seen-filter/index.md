//[elysianmuse](../../../index.md)/[org.darkSolace.muse.lastSeen.service](../index.md)/[LastSeenFilter](index.md)

# LastSeenFilter

[jvm]\
@Component

class [LastSeenFilter](index.md) : OncePerRequestFilter

Filter to create or update [org.darkSolace.muse.lastSeen.model.LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md) for each request

#### See also

jvm

| |
|---|
| OncePerRequestFilter |

## Constructors

| | |
|---|---|
| [LastSeenFilter](-last-seen-filter.md) | [jvm]<br>fun [LastSeenFilter](-last-seen-filter.md)() |

## Functions

| Name | Summary |
|---|---|
| [afterPropertiesSet](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#2115246148%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [afterPropertiesSet](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#2115246148%2FFunctions%2F-1216412040)() |
| [destroy](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#-1289270679%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [destroy](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#-1289270679%2FFunctions%2F-1216412040)() |
| [doFilter](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#-1038434982%2FFunctions%2F-1216412040) | [jvm]<br>override fun [doFilter](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#-1038434982%2FFunctions%2F-1216412040)(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) |
| [init](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#-1306378036%2FFunctions%2F-1216412040) | [jvm]<br>override fun [init](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#-1306378036%2FFunctions%2F-1216412040)(filterConfig: FilterConfig) |
| [setBeanName](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#719905502%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setBeanName](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#719905502%2FFunctions%2F-1216412040)(beanName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [environment](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#179565424%2FProperties%2F-1216412040) | [jvm]<br>@Nullable<br>var [environment](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#179565424%2FProperties%2F-1216412040): Environment |
| [filterConfig](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#653156989%2FProperties%2F-1216412040) | [jvm]<br>@Nullable<br>@get:Nullable<br>val [filterConfig](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#653156989%2FProperties%2F-1216412040): FilterConfig |
| [jwtUtils](jwt-utils.md) | [jvm]<br>@Autowired<br>lateinit var [jwtUtils](jwt-utils.md): [JwtUtils](../../org.darkSolace.muse.security.service/-jwt-utils/index.md) |
| [lastSeenService](last-seen-service.md) | [jvm]<br>@Autowired<br>lateinit var [lastSeenService](last-seen-service.md): [LastSeenService](../-last-seen-service/index.md) |
| [servletContext](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#1632496429%2FProperties%2F-1216412040) | [jvm]<br>@Nullable<br>protected var [servletContext](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md#1632496429%2FProperties%2F-1216412040): ServletContext |
