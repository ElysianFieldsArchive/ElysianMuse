//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.service](../index.md)/[AuthEntryPointJwt](index.md)

# AuthEntryPointJwt

@Component

class [AuthEntryPointJwt](index.md) : AuthenticationEntryPoint

Component to handle unauthorised access to an endpoint

#### See also

| |
|---|
| AuthenticationEntryPoint |

## Constructors

| | |
|---|---|
| [AuthEntryPointJwt](-auth-entry-point-jwt.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [commence](commence.md) | [jvm]<br>open override fun [commence](commence.md)(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?)<br>Handles the request when an endpoint is accessed without authorisation. Leads to an 401 UNAUTHORIZED HTTP Status code. |
