//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.model](../index.md)/[MailTemplate](index.md)

# MailTemplate

[jvm]\
@Entity

class [MailTemplate](index.md)(val type: [TemplateType](../-template-type/index.md)? = null)

Model class representing a [MailTemplate](index.md) to be used to send out standardized emails. The template content can contain variables to be substituted during email creation.

## Constructors

| | |
|---|---|
| [MailTemplate](-mail-template.md) | [jvm]<br>fun [MailTemplate](-mail-template.md)(type: [TemplateType](../-template-type/index.md)? = null) |

## Properties

| Name | Summary |
|---|---|
| [id](id.md) | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [mailContent](mail-content.md) | [jvm]<br>var [mailContent](mail-content.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [mailSubject](mail-subject.md) | [jvm]<br>var [mailSubject](mail-subject.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [templateVars](template-vars.md) | [jvm]<br>val [templateVars](template-vars.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[MailTemplateVar](../-mail-template-var/index.md)&gt; |
| [templateVarValues](template-var-values.md) | [jvm]<br>val [templateVarValues](template-var-values.md): [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [type](type.md) | [jvm]<br>val [type](type.md): [TemplateType](../-template-type/index.md)? = null |
