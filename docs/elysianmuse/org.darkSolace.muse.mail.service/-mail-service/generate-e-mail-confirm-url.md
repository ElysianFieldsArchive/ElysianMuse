//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.service](../index.md)/[MailService](index.md)/[generateEMailConfirmUrl](generate-e-mail-confirm-url.md)

# generateEMailConfirmUrl

[jvm]\
fun [generateEMailConfirmUrl](generate-e-mail-confirm-url.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), siteUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Generates a confirm-url used to verify an email address.

#### Return

the email confirm url

#### Parameters

jvm

| | |
|---|---|
| user | the user for which the confirm-url is created |
| siteUrl | the base url of the current site |

#### Throws

|                                                                                            |                         |
|--------------------------------------------------------------------------------------------|-------------------------|
| [TemplateException](../../org.darkSolace.muse.mail.exception/-template-exception/index.md) | if the site url is null |
