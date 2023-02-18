//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)/[confirmSuspension](confirm-suspension.md)

# confirmSuspension

[jvm]\

@PostMapping(value = [&quot;/suspend/confirm/{confirmationCode}&quot;])

fun [confirmSuspension](confirm-suspension.md)(@PathVariableconfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Confirms a [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md), identified by its confirmation code.

#### Return

HTTP 200 on successful confirmation, HTTP 400 otherwise

#### Samples

#### Parameters

jvm

| | |
|---|---|
| confirmationCode | the confirmation code |
