//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statisticsModule.service](../index.md)/[LastSeenFilter](index.md)

# LastSeenFilter

[jvm]\
@Component

class [LastSeenFilter](index.md) : OncePerRequestFilter

Filter to create or update [org.darkSolace.muse.statisticsModule.model.LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) for each request

## See also

jvm

| | |
|---|---|
| org.springframework.web.filter.OncePerRequestFilter |  |

## Constructors

| | |
|---|---|
| [LastSeenFilter](-last-seen-filter.md) | [jvm]<br>fun [LastSeenFilter](-last-seen-filter.md)() |

## Functions

| Name | Summary |
|---|---|
| [addRequiredProperty](index.md#-803324074%2FFunctions%2F-1216412040) | [jvm]<br>fun [addRequiredProperty](index.md#-803324074%2FFunctions%2F-1216412040)(property: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [afterPropertiesSet](index.md#2115246148%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [afterPropertiesSet](index.md#2115246148%2FFunctions%2F-1216412040)() |
| [createEnvironment](index.md#-840313332%2FFunctions%2F-1216412040) | [jvm]<br>open fun [createEnvironment](index.md#-840313332%2FFunctions%2F-1216412040)(): Environment |
| [destroy](index.md#-1289270679%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [destroy](index.md#-1289270679%2FFunctions%2F-1216412040)() |
| [doFilter](index.md#-1038434982%2FFunctions%2F-1216412040) | [jvm]<br>override fun [doFilter](index.md#-1038434982%2FFunctions%2F-1216412040)(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) |
| [doFilterNestedErrorDispatch](index.md#896588569%2FFunctions%2F-1216412040) | [jvm]<br>open fun [doFilterNestedErrorDispatch](index.md#896588569%2FFunctions%2F-1216412040)(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) |
| [getAlreadyFilteredAttributeName](index.md#2023091357%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getAlreadyFilteredAttributeName](index.md#2023091357%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getEnvironment](index.md#-209554086%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [getEnvironment](index.md#-209554086%2FFunctions%2F-1216412040)(): Environment |
| [getFilterConfig](index.md#1475354067%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getFilterConfig](index.md#1475354067%2FFunctions%2F-1216412040)(): FilterConfig |
| [getFilterName](index.md#1782161578%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getFilterName](index.md#1782161578%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getServletContext](index.md#1489905923%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getServletContext](index.md#1489905923%2FFunctions%2F-1216412040)(): ServletContext |
| [init](index.md#-1306378036%2FFunctions%2F-1216412040) | [jvm]<br>override fun [init](index.md#-1306378036%2FFunctions%2F-1216412040)(filterConfig: FilterConfig) |
| [initBeanWrapper](index.md#-1426054111%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initBeanWrapper](index.md#-1426054111%2FFunctions%2F-1216412040)(bw: BeanWrapper) |
| [initFilterBean](index.md#456629791%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initFilterBean](index.md#456629791%2FFunctions%2F-1216412040)() |
| [isAsyncDispatch](index.md#-462153524%2FFunctions%2F-1216412040) | [jvm]<br>open fun [isAsyncDispatch](index.md#-462153524%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAsyncStarted](index.md#1297640843%2FFunctions%2F-1216412040) | [jvm]<br>open fun [isAsyncStarted](index.md#1297640843%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setBeanName](index.md#719905502%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setBeanName](index.md#719905502%2FFunctions%2F-1216412040)(beanName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setEnvironment](index.md#-1350385156%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setEnvironment](index.md#-1350385156%2FFunctions%2F-1216412040)(environment: Environment) |
| [setServletContext](index.md#-2022047700%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setServletContext](index.md#-2022047700%2FFunctions%2F-1216412040)(servletContext: ServletContext) |
| [shouldNotFilter](index.md#965863800%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilter](index.md#965863800%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldNotFilterAsyncDispatch](index.md#-553183887%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilterAsyncDispatch](index.md#-553183887%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldNotFilterErrorDispatch](index.md#514090853%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilterErrorDispatch](index.md#514090853%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [jwtUtils](jwt-utils.md) | [jvm]<br>@Autowired<br>lateinit var [jwtUtils](jwt-utils.md): [JwtUtils](../../org.darkSolace.muse.securityModule.service/-jwt-utils/index.md) |
| [lastSeenService](last-seen-service.md) | [jvm]<br>@Autowired<br>lateinit var [lastSeenService](last-seen-service.md): [LastSeenService](../-last-seen-service/index.md) |
| [logger](index.md#925176327%2FProperties%2F-1216412040) | [jvm]<br>val [logger](index.md#925176327%2FProperties%2F-1216412040): Log |
