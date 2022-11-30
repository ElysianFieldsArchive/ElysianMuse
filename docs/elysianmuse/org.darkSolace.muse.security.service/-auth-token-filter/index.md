//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.service](../index.md)/[AuthTokenFilter](index.md)

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
| [addRequiredProperty](index.md#-803324074%2FFunctions%2F-1216412040) | [jvm]<br>fun [addRequiredProperty](index.md#-803324074%2FFunctions%2F-1216412040)(property: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [afterPropertiesSet](index.md#2115246148%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [afterPropertiesSet](index.md#2115246148%2FFunctions%2F-1216412040)() |
| [createEnvironment](index.md#-840313332%2FFunctions%2F-1216412040) | [jvm]<br>open fun [createEnvironment](index.md#-840313332%2FFunctions%2F-1216412040)(): Environment |
| [destroy](index.md#-1289270679%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [destroy](index.md#-1289270679%2FFunctions%2F-1216412040)() |
| [doFilter](index.md#424373182%2FFunctions%2F-1216412040) | [jvm]<br>override fun [doFilter](index.md#424373182%2FFunctions%2F-1216412040)(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) |
| [doFilterNestedErrorDispatch](index.md#845946741%2FFunctions%2F-1216412040) | [jvm]<br>open fun [doFilterNestedErrorDispatch](index.md#845946741%2FFunctions%2F-1216412040)(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) |
| [getAlreadyFilteredAttributeName](index.md#2023091357%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getAlreadyFilteredAttributeName](index.md#2023091357%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getEnvironment](index.md#-209554086%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [getEnvironment](index.md#-209554086%2FFunctions%2F-1216412040)(): Environment |
| [getFilterConfig](index.md#1475354067%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getFilterConfig](index.md#1475354067%2FFunctions%2F-1216412040)(): FilterConfig |
| [getFilterName](index.md#1782161578%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getFilterName](index.md#1782161578%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getServletContext](index.md#1489905923%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getServletContext](index.md#1489905923%2FFunctions%2F-1216412040)(): ServletContext |
| [init](index.md#-1834940120%2FFunctions%2F-1216412040) | [jvm]<br>override fun [init](index.md#-1834940120%2FFunctions%2F-1216412040)(filterConfig: FilterConfig) |
| [initBeanWrapper](index.md#-1426054111%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initBeanWrapper](index.md#-1426054111%2FFunctions%2F-1216412040)(bw: BeanWrapper) |
| [initFilterBean](index.md#456629791%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initFilterBean](index.md#456629791%2FFunctions%2F-1216412040)() |
| [isAsyncDispatch](index.md#494062832%2FFunctions%2F-1216412040) | [jvm]<br>open fun [isAsyncDispatch](index.md#494062832%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAsyncStarted](index.md#-560665105%2FFunctions%2F-1216412040) | [jvm]<br>open fun [isAsyncStarted](index.md#-560665105%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setBeanName](index.md#719905502%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setBeanName](index.md#719905502%2FFunctions%2F-1216412040)(beanName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setEnvironment](index.md#-1350385156%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setEnvironment](index.md#-1350385156%2FFunctions%2F-1216412040)(environment: Environment) |
| [setServletContext](index.md#-37321336%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [setServletContext](index.md#-37321336%2FFunctions%2F-1216412040)(servletContext: ServletContext) |
| [shouldNotFilter](index.md#-1570823524%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilter](index.md#-1570823524%2FFunctions%2F-1216412040)(request: HttpServletRequest): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldNotFilterAsyncDispatch](index.md#-553183887%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilterAsyncDispatch](index.md#-553183887%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldNotFilterErrorDispatch](index.md#514090853%2FFunctions%2F-1216412040) | [jvm]<br>open fun [shouldNotFilterErrorDispatch](index.md#514090853%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [jwtUtils](jwt-utils.md) | [jvm]<br>@Autowired<br>lateinit var [jwtUtils](jwt-utils.md): [JwtUtils](../-jwt-utils/index.md) |
| [logger](index.md#925176327%2FProperties%2F-1216412040) | [jvm]<br>val [logger](index.md#925176327%2FProperties%2F-1216412040): Log |
| [userDetailsService](user-details-service.md) | [jvm]<br>@Autowired<br>lateinit var [userDetailsService](user-details-service.md): [UserDetailsService](../-user-details-service/index.md) |
