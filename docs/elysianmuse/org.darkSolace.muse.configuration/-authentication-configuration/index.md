//[elysianmuse](../../../index.md)/[org.darkSolace.muse.configuration](../index.md)/[AuthenticationConfiguration](index.md)

# AuthenticationConfiguration

[jvm]\
@Configuration

class [AuthenticationConfiguration](index.md)(@AutowireduserDetailsService: [UserDetailsService](../../org.darkSolace.muse.security.service/-user-details-service/index.md), @AutowiredpasswordEncoder: BCryptPasswordEncoder)

## Constructors

| | |
|---|---|
| [AuthenticationConfiguration](-authentication-configuration.md) | [jvm]<br>constructor(@AutowireduserDetailsService: [UserDetailsService](../../org.darkSolace.muse.security.service/-user-details-service/index.md), @AutowiredpasswordEncoder: BCryptPasswordEncoder) |

## Functions

| Name | Summary |
|---|---|
| [authenticationManager](authentication-manager.md) | [jvm]<br>@Bean<br>fun [authenticationManager](authentication-manager.md)(http: HttpSecurity): AuthenticationManager<br>Configures the AuthenticationManager, via AuthenticationManagerBuilder, to use [UserDetailsService](../../org.darkSolace.muse.security.service/-user-details-service/index.md) and BCryptPasswordEncoder |
