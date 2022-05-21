//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.model](../index.md)/[MailTemplateVar](index.md)

# MailTemplateVar

[jvm]\
@Entity

data class [MailTemplateVar](index.md)(val templateVar: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, val systemManaged: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

A [MailTemplateVar](index.md) can be used to create variable content within a [MailTemplate](../-mail-template/index.md). [MailTemplateVar](index.md)s have to be specified as &quot;<$templateVar>&quot; within the template.

## Constructors

| | |
|---|---|
| [MailTemplateVar](-mail-template-var.md) | [jvm]<br>fun [MailTemplateVar](-mail-template-var.md)(templateVar: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, systemManaged: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [jvm]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>val [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [systemManaged](system-managed.md) | [jvm]<br>val [systemManaged](system-managed.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [templateVar](template-var.md) | [jvm]<br>val [templateVar](template-var.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
