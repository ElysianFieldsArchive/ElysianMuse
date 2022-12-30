//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[SuspensionService](index.md)/[confirmSuspension](confirm-suspension.md)

# confirmSuspension

[jvm]\
fun [confirmSuspension](confirm-suspension.md)(confirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Confirms the acceptance of a [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md), identified by its confirmation code.

#### Return

boolean if the acceptance was successful

#### Parameters

jvm

| | |
|---|---|
| confirmationCode | the confirmation code belonging to the [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md) to be accepted |
