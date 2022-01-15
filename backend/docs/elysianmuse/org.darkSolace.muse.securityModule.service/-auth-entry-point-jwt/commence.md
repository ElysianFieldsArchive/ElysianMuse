//[elysianmuse](../../../index.md)/[org.darkSolace.muse.securityModule.service](../index.md)
/[AuthEntryPointJwt](index.md)/[commence](commence.md)

# commence

[jvm]\
open override fun [commence](commence.md)(request: HttpServletRequest?, response: HttpServletResponse?, authException:
AuthenticationException?)

Handles the request when an endpoint is accessed without authorisation. Leads to an 401 UNAUTHORIZED HTTP Status code.
