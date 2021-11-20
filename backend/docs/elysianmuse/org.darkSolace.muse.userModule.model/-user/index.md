//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.model](../index.md)/[User](index.md)

# User

[jvm]\
@Entity

data class [User](index.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?,
username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html),
password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html),
salt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html),
email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html),
realName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?,
signUpDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html),
lastLogInDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)?,
bio: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?,
birthday: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)?,
validatedAuthor: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html),
onProbation: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html),
userSettings: [UserSettings](../-user-settings/index.md),
userTags: [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[UserTag](
../-user-tag/index.md)&gt;, avatar: [Avatar](../-avatar/index.md)?, role: [Role](../-role/index.md))

The [User](index.md) model class

Holds all values containing to a registered user.

## Constructors

| | |
|---|---|
| [User](-user.md) | [jvm]<br>fun [User](-user.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), salt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = BCrypt.gensalt(), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), realName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, signUpDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), lastLogInDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, bio: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, birthday: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, validatedAuthor: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, onProbation: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, userSettings: [UserSettings](../-user-settings/index.md) = UserSettings(), userTags: [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[UserTag](../-user-tag/index.md)&gt; = mutableSetOf(), avatar: [Avatar](../-avatar/index.md)? = null, role: [Role](../-role/index.md) = Role.MEMBER) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [avatar](avatar.md) | [jvm]<br>var [avatar](avatar.md): [Avatar](../-avatar/index.md)? = null |
| [bio](bio.md) | [jvm]<br>var [bio](bio.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [birthday](birthday.md) | [jvm]<br>var [birthday](birthday.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null |
| [email](email.md) | [jvm]<br>var [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [lastLogInDate](last-log-in-date.md) | [jvm]<br>var [lastLogInDate](last-log-in-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null |
| [onProbation](on-probation.md) | [jvm]<br>var [onProbation](on-probation.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [password](password.md) | [jvm]<br>var [password](password.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [realName](real-name.md) | [jvm]<br>var [realName](real-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [role](role.md) | [jvm]<br>var [role](role.md): [Role](../-role/index.md) |
| [salt](salt.md) | [jvm]<br>var [salt](salt.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [signUpDate](sign-up-date.md) | [jvm]<br>val [signUpDate](sign-up-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
| [username](username.md) | [jvm]<br>val [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [userSettings](user-settings.md) | [jvm]<br>var [userSettings](user-settings.md): [UserSettings](../-user-settings/index.md) |
| [userTags](user-tags.md) | [jvm]<br>val [userTags](user-tags.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[UserTag](../-user-tag/index.md)&gt; |
| [validatedAuthor](validated-author.md) | [jvm]<br>var [validatedAuthor](validated-author.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
