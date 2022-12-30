//[elysianmuse](../../../index.md)/[org.darkSolace.muse.security.service](../index.md)/[UserDetailsService](index.md)

# UserDetailsService

[jvm]\
@Service

class [UserDetailsService](index.md)(@Autowiredval userRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md)) : UserDetailsService

Service to handle everything in regard to UserDetails.

#### See also

jvm

| |
|---|
| UserDetailsService |

## Constructors

| | |
|---|---|
| [UserDetailsService](-user-details-service.md) | [jvm]<br>fun [UserDetailsService](-user-details-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [loadUserByUsername](load-user-by-username.md) | [jvm]<br>open override fun [loadUserByUsername](load-user-by-username.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): UserDetails<br>Loads a [org.darkSolace.muse.user.model.User](../../org.darkSolace.muse.user.model/-user/index.md) and creates the corresponding UserDetails |

## Properties

| Name | Summary |
|---|---|
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md) |
