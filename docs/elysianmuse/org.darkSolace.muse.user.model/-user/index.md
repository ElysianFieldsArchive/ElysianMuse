//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.model](../index.md)/[User](index.md)

# User

[jvm]\
@Entity

data class [User](index.md)(var id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var salt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = BCrypt.gensalt(), var email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var realName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val signUpDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), var lastLogInDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, var bio: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, var birthday: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, var validatedAuthor: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, var onProbation: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, var userSettings: [UserSettings](../-user-settings/index.md) = UserSettings(), val userTags: [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[UserTag](../-user-tag/index.md)&gt; = mutableSetOf(), var avatar: [Avatar](../-avatar/index.md)? = null, var role: [Role](../-role/index.md) = Role.MEMBER, var emailConfirmed: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, var emailConfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

The [User](index.md) model class

Holds all values containing to a registered user.

## Constructors

| | |
|---|---|
| [User](-user.md) | [jvm]<br>constructor(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), salt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = BCrypt.gensalt(), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), realName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, signUpDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), lastLogInDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, bio: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, birthday: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, validatedAuthor: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, onProbation: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, userSettings: [UserSettings](../-user-settings/index.md) = UserSettings(), userTags: [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[UserTag](../-user-tag/index.md)&gt; = mutableSetOf(), avatar: [Avatar](../-avatar/index.md)? = null, role: [Role](../-role/index.md) = Role.MEMBER, emailConfirmed: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, emailConfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toPublicUser](to-public-user.md) | [jvm]<br>fun [toPublicUser](to-public-user.md)(): [User](index.md) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [avatar](avatar.md) | [jvm]<br>var [avatar](avatar.md): [Avatar](../-avatar/index.md)? |
| [bio](bio.md) | [jvm]<br>var [bio](bio.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [birthday](birthday.md) | [jvm]<br>var [birthday](birthday.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? |
| [email](email.md) | [jvm]<br>var [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [emailConfirmationCode](email-confirmation-code.md) | [jvm]<br>var [emailConfirmationCode](email-confirmation-code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [emailConfirmed](email-confirmed.md) | [jvm]<br>var [emailConfirmed](email-confirmed.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [id](id.md) | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [lastLogInDate](last-log-in-date.md) | [jvm]<br>var [lastLogInDate](last-log-in-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? |
| [onProbation](on-probation.md) | [jvm]<br>var [onProbation](on-probation.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [password](password.md) | [jvm]<br>var [password](password.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [realName](real-name.md) | [jvm]<br>var [realName](real-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [role](role.md) | [jvm]<br>var [role](role.md): [Role](../-role/index.md) |
| [salt](salt.md) | [jvm]<br>var [salt](salt.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [signUpDate](sign-up-date.md) | [jvm]<br>val [signUpDate](sign-up-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
| [username](username.md) | [jvm]<br>val [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [userSettings](user-settings.md) | [jvm]<br>var [userSettings](user-settings.md): [UserSettings](../-user-settings/index.md) |
| [userTags](user-tags.md) | [jvm]<br>val [userTags](user-tags.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[UserTag](../-user-tag/index.md)&gt; |
| [validatedAuthor](validated-author.md) | [jvm]<br>var [validatedAuthor](validated-author.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
