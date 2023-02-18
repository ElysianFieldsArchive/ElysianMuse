//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.service](../index.md)/[MailService](index.md)/[generateResetPasswordUrl](generate-reset-password-url.md)

# generateResetPasswordUrl

[jvm]\
fun [generateResetPasswordUrl](generate-reset-password-url.md)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), siteUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Generates a password reset url

#### Return

the password reset url

#### Parameters

jvm

| | |
|---|---|
| code | the reset code used in the reset url |
| siteUrl | the base url of the current site |

#### Throws

| | |
|---|---|
| [TemplateException](../../org.darkSolace.muse.mail.exception/-template-exception/index.md) | if the site url is null |
