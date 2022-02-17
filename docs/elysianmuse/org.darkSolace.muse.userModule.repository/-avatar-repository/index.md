//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.repository](../index.md)/[AvatarRepository](index.md)

# AvatarRepository

[jvm]\
@Repository

interface [AvatarRepository](index.md) : CrudRepository&lt;[Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md), [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt; 

CRUD Repository for the [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md) entity.

## Functions

| Name | Summary |
|---|---|
| [count](../-user-settings-repository/index.md#-1347258675%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [count](../-user-settings-repository/index.md#-1347258675%2FFunctions%2F-1216412040)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [delete](index.md#422647603%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [delete](index.md#422647603%2FFunctions%2F-1216412040)(entity: [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)) |
| [deleteAll](../-user-settings-repository/index.md#87931462%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [deleteAll](../-user-settings-repository/index.md#87931462%2FFunctions%2F-1216412040)()<br>abstract fun [deleteAll](index.md#312126962%2FFunctions%2F-1216412040)(entities: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)&gt;) |
| [deleteAllById](../-user-settings-repository/index.md#897308593%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [deleteAllById](../-user-settings-repository/index.md#897308593%2FFunctions%2F-1216412040)(ids: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt;) |
| [deleteById](../-user-settings-repository/index.md#-1865927624%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [deleteById](../-user-settings-repository/index.md#-1865927624%2FFunctions%2F-1216412040)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [existsById](../-user-settings-repository/index.md#-1245749783%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [existsById](../-user-settings-repository/index.md#-1245749783%2FFunctions%2F-1216412040)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [findAll](../-user-settings-repository/index.md#432803092%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [findAll](../-user-settings-repository/index.md#432803092%2FFunctions%2F-1216412040)(): [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)&gt; |
| [findAllById](../-user-settings-repository/index.md#-2014544349%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [findAllById](../-user-settings-repository/index.md#-2014544349%2FFunctions%2F-1216412040)(ids: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt;): [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)&gt; |
| [findById](../-user-settings-repository/index.md#635093510%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [findById](../-user-settings-repository/index.md#635093510%2FFunctions%2F-1216412040)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)&lt;[Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)&gt; |
| [save](index.md#-340270783%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun &lt;[S](index.md#-340270783%2FFunctions%2F-1216412040) : [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)&gt; [save](index.md#-340270783%2FFunctions%2F-1216412040)(entity: [S](index.md#-340270783%2FFunctions%2F-1216412040)): [S](index.md#-340270783%2FFunctions%2F-1216412040) |
| [saveAll](index.md#-1747135708%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun &lt;[S](index.md#-1747135708%2FFunctions%2F-1216412040) : [Avatar](../../org.darkSolace.muse.userModule.model/-avatar/index.md)&gt; [saveAll](index.md#-1747135708%2FFunctions%2F-1216412040)(entities: [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[S](index.md#-1747135708%2FFunctions%2F-1216412040)&gt;): [MutableIterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-iterable/index.html)&lt;[S](index.md#-1747135708%2FFunctions%2F-1216412040)&gt; |
