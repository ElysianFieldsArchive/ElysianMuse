//[elysianmuse](../../../index.md)/[org.darkSolace.muse.configuration](../index.md)/[AuthenticationDsl](index.md)

# AuthenticationDsl

[jvm]\
@Component

class [AuthenticationDsl](index.md) : AbstractHttpConfigurer&lt;[AuthenticationDsl](index.md), HttpSecurity&gt;

## Constructors

| | |
|---|---|
| [AuthenticationDsl](-authentication-dsl.md) | [jvm]<br>fun [AuthenticationDsl](-authentication-dsl.md)() |

## Functions

| Name | Summary |
|---|---|
| [addObjectPostProcessor](index.md#1193688233%2FFunctions%2F-1216412040) | [jvm]<br>open fun [addObjectPostProcessor](index.md#1193688233%2FFunctions%2F-1216412040)(objectPostProcessor: ObjectPostProcessor&lt;*&gt;) |
| [and](index.md#-603621395%2FFunctions%2F-1216412040) | [jvm]<br>open fun [and](index.md#-603621395%2FFunctions%2F-1216412040)(): HttpSecurity |
| [authenticationManager](authentication-manager.md) | [jvm]<br>@Bean<br>fun [authenticationManager](authentication-manager.md)(http: HttpSecurity): AuthenticationManager |
| [configure](index.md#1980644820%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [configure](index.md#1980644820%2FFunctions%2F-1216412040)(builder: HttpSecurity) |
| [disable](index.md#-2126707726%2FFunctions%2F-1216412040) | [jvm]<br>open fun [disable](index.md#-2126707726%2FFunctions%2F-1216412040)(): HttpSecurity |
| [getBuilder](index.md#2141346801%2FFunctions%2F-1216412040) | [jvm]<br>fun [getBuilder](index.md#2141346801%2FFunctions%2F-1216412040)(): HttpSecurity |
| [init](index.md#1151727804%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [init](index.md#1151727804%2FFunctions%2F-1216412040)(builder: HttpSecurity) |
| [postProcess](index.md#991065922%2FFunctions%2F-1216412040) | [jvm]<br>open fun &lt;[T](index.md#991065922%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [postProcess](index.md#991065922%2FFunctions%2F-1216412040)(object: [T](index.md#991065922%2FFunctions%2F-1216412040)): [T](index.md#991065922%2FFunctions%2F-1216412040) |
| [setBuilder](index.md#-276096173%2FFunctions%2F-1216412040) | [jvm]<br>open fun [setBuilder](index.md#-276096173%2FFunctions%2F-1216412040)(builder: HttpSecurity) |
| [withObjectPostProcessor](index.md#8694784%2FFunctions%2F-1216412040) | [jvm]<br>open fun [withObjectPostProcessor](index.md#8694784%2FFunctions%2F-1216412040)(objectPostProcessor: ObjectPostProcessor&lt;*&gt;): [AuthenticationDsl](index.md) |
