//[elysianmuse](../../../index.md)/[org.darkSolace.muse.configuration](../index.md)/[AuthenticationConfiguration](index.md)/[authenticationManager](authentication-manager.md)

# authenticationManager

[jvm]\

@Bean

fun [authenticationManager](authentication-manager.md)(http: HttpSecurity): AuthenticationManager

Configures the AuthenticationManager, via AuthenticationManagerBuilder, to use [UserDetailsService](../../org.darkSolace.muse.security.service/-user-details-service/index.md) and BCryptPasswordEncoder

#### Return

configured AuthenticationManager
