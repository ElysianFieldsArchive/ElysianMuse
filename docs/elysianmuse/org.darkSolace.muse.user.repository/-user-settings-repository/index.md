//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.repository](../index.md)/[UserSettingsRepository](index.md)

# UserSettingsRepository

[jvm]\
@Repository

interface [UserSettingsRepository](index.md) : CrudRepository&lt;[UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md), [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt; 

CRUD Repository for the [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md) entity.

## Functions

| Name | Summary |
|---|---|
| [count](index.md#-1347258675%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [count](index.md#-1347258675%2FFunctions%2F-1216412040)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [delete](index.md#1626245522%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [delete](index.md#1626245522%2FFunctions%2F-1216412040)(entity: [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)) |
| [deleteAll](index.md#87931462%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [deleteAll](index.md#87931462%2FFunctions%2F-1216412040)()<br>abstract fun [deleteAll](index.md#-1031043213%2FFunctions%2F-1216412040)(entities: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)&gt;) |
| [deleteAllById](index.md#897308593%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [deleteAllById](index.md#897308593%2FFunctions%2F-1216412040)(ids: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt;) |
| [deleteById](index.md#-1865927624%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [deleteById](index.md#-1865927624%2FFunctions%2F-1216412040)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [existsById](index.md#-1245749783%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [existsById](index.md#-1245749783%2FFunctions%2F-1216412040)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [findAll](index.md#432803092%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [findAll](index.md#432803092%2FFunctions%2F-1216412040)(): [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)&gt; |
| [findAllById](index.md#-2014544349%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [findAllById](index.md#-2014544349%2FFunctions%2F-1216412040)(ids: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt;): [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)&gt; |
| [findById](index.md#635093510%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [findById](index.md#635093510%2FFunctions%2F-1216412040)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)&lt;[UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)&gt; |
| [save](index.md#971126752%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun &lt;[S](index.md#971126752%2FFunctions%2F-1216412040) : [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)&gt; [save](index.md#971126752%2FFunctions%2F-1216412040)(entity: [S](index.md#971126752%2FFunctions%2F-1216412040)): [S](index.md#971126752%2FFunctions%2F-1216412040) |
| [saveAll](index.md#251482213%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun &lt;[S](index.md#251482213%2FFunctions%2F-1216412040) : [UserSettings](../../org.darkSolace.muse.user.model/-user-settings/index.md)&gt; [saveAll](index.md#251482213%2FFunctions%2F-1216412040)(entities: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[S](index.md#251482213%2FFunctions%2F-1216412040)&gt;): [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[S](index.md#251482213%2FFunctions%2F-1216412040)&gt; |
