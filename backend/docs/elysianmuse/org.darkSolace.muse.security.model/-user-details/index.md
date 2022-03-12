//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.model](../index.md)/[UserDetails](index.md)

# UserDetails

[jvm]\
class [UserDetails](index.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)?) : UserDetails

UserDetails model to pass required information to
the [org.darkSolace.muse.security.service.AuthTokenFilter](../../org.darkSolace.muse.security.service/-auth-token-filter/index.md)

## Constructors

| | |
|---|---|
| [UserDetails](-user-details.md) | [jvm]<br>fun [UserDetails](-user-details.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)?) |

## Functions

| Name | Summary |
|---|---|
| [getAuthorities](get-authorities.md) | [jvm]<br>open override fun [getAuthorities](get-authorities.md)(): [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;GrantedAuthority&gt; |
| [getPassword](get-password.md) | [jvm]<br>open override fun [getPassword](get-password.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getUsername](get-username.md) | [jvm]<br>open override fun [getUsername](get-username.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [isAccountNonExpired](is-account-non-expired.md) | [jvm]<br>open override fun [isAccountNonExpired](is-account-non-expired.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAccountNonLocked](is-account-non-locked.md) | [jvm]<br>open override fun [isAccountNonLocked](is-account-non-locked.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isCredentialsNonExpired](is-credentials-non-expired.md) | [jvm]<br>open override fun [isCredentialsNonExpired](is-credentials-non-expired.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isEnabled](is-enabled.md) | [jvm]<br>open override fun [isEnabled](is-enabled.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [user](user.md) | [jvm]<br>val [user](user.md): [User](../../org.darkSolace.muse.user.model/-user/index.md)? |
