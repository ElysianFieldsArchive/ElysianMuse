//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.service](../index.md)/[AuthTokenFilter](index.md)

# AuthTokenFilter

[jvm]\
@Component

class [AuthTokenFilter](index.md) : OncePerRequestFilter

Filter to check the provided authorization for validity

## See also

jvm

| | |
|---|---|
| org.springframework.web.filter.OncePerRequestFilter |  |

## Constructors

| | |
|---|---|
| [AuthTokenFilter](-auth-token-filter.md) | [jvm]<br>fun [AuthTokenFilter](-auth-token-filter.md)() |

## Functions

| Name | Summary |
|---|---|
| [addRequiredProperty](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-803324074%2FFunctions%2F-1216412040) | [jvm]<br>fun [addRequiredProperty](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-803324074%2FFunctions%2F-1216412040)(property: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [afterPropertiesSet](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#2115246148%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [afterPropertiesSet](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#2115246148%2FFunctions%2F-1216412040)() |
| [createEnvironment](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-840313332%2FFunctions%2F-1216412040) | [jvm]<br>open fun [createEnvironment](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-840313332%2FFunctions%2F-1216412040)(): Environment |
| [destroy](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1289270679%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [destroy](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1289270679%2FFunctions%2F-1216412040)() |
| [doFilter](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1038434982%2FFunctions%2F-1216412040) | [jvm]<br>override fun [doFilter](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1038434982%2FFunctions%2F-1216412040)(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) |
| [doFilterNestedErrorDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#896588569%2FFunctions%2F-1216412040) | [jvm]<br>open fun [doFilterNestedErrorDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#896588569%2FFunctions%2F-1216412040)(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) |
| [getAlreadyFilteredAttributeName](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#2023091357%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getAlreadyFilteredAttributeName](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#2023091357%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getEnvironment](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-209554086%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [getEnvironment](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-209554086%2FFunctions%2F-1216412040)(): Environment |
| [getFilterConfig](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1475354067%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getFilterConfig](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1475354067%2FFunctions%2F-1216412040)(): FilterConfig |
| [getFilterName](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1782161578%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getFilterName](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1782161578%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getServletContext](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1489905923%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getServletContext](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1489905923%2FFunctions%2F-1216412040)(): ServletContext |
| [init](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1306378036%2FFunctions%2F-1216412040) | [jvm]<br>override fun [init](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1306378036%2FFunctions%2F-1216412040)(filterConfig: FilterConfig) |
| [initBeanWrapper](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1426054111%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initBeanWrapper](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1426054111%2FFunctions%2F-1216412040)(bw: BeanWrapper) |
| [initFilterBean](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#456629791%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initFilterBean](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#456629791%2FFunctions%2F-1216412040)() |
| [isAsyncDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-462153524%2FFunctions%2F-1216412040) | [jvm]<br>open fun [isAsyncDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-462153524%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAsyncStarted](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1297640843%2FFunctions%2F-1216412040) | [jvm]<br>open fun [isAsyncStarted](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#1297640843%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setBeanName](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#719905502%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setBeanName](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#719905502%2FFunctions%2F-1216412040)(beanName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setEnvironment](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1350385156%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setEnvironment](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-1350385156%2FFunctions%2F-1216412040)(environment: Environment) |
| [setServletContext](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-2022047700%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setServletContext](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-2022047700%2FFunctions%2F-1216412040)(servletContext: ServletContext) |
| [shouldNotFilter](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#965863800%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilter](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#965863800%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldNotFilterAsyncDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-553183887%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilterAsyncDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#-553183887%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldNotFilterErrorDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#514090853%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilterErrorDispatch](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#514090853%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [jwtUtils](jwt-utils.md) | [jvm]<br>@Autowired<br>lateinit var [jwtUtils](jwt-utils.md): [JwtUtils](../-jwt-utils/index.md) |
| [logger](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#925176327%2FProperties%2F-1216412040) | [jvm]<br>val [logger](../../org.darkSolace.muse.statisticsModule.service/-last-seen-filter/index.md#925176327%2FProperties%2F-1216412040): Log |
| [userDetailsService](user-details-service.md) | [jvm]<br>@Autowired<br>lateinit var [userDetailsService](user-details-service.md): [UserDetailsService](../-user-details-service/index.md) |
