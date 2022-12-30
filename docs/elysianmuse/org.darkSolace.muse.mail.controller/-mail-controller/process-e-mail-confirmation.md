//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.controller](../index.md)/[MailController](index.md)/[processEMailConfirmation](process-e-mail-confirmation.md)

# processEMailConfirmation

[jvm]\

@GetMapping(value = [&quot;/confirm/{code}&quot;])

fun [processEMailConfirmation](process-e-mail-confirmation.md)(@PathVariablecode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Validates a users email address by a validation code sent to the provided email address.

#### Return

Http status 200 after a successful validation, 400 otherwise

#### Samples

#### Parameters

jvm

| | |
|---|---|
| code | the validation code sent to the provided email |
