//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.controller](../index.md)/[UserRestController](index.md)/[confirmSuspension](confirm-suspension.md)

# confirmSuspension

[jvm]\

@PostMapping(value = ["/suspend/confirm/{confirmationCode}"])

fun [confirmSuspension](confirm-suspension.md)(@PathVariableconfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;*&gt;

Confirms a [SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md), identified by its confirmation code.

#### Return

HTTP 200 on successful confirmation, HTTP 400 otherwise

## Samples

## Parameters

jvm

| | |
|---|---|
| confirmationCode | the confirmation code |
