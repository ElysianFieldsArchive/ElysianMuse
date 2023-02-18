//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserPasswordService](index.md)

# UserPasswordService

[jvm]\
@Service

class [UserPasswordService](index.md)(@Autowiredval passwordResetRequestRepository: [PasswordResetRequestRepository](../../org.darkSolace.muse.user.repository/-password-reset-request-repository/index.md), @Autowiredval userRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @Autowiredval mailService: [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md))

Service for dealing with password changes

## Constructors

| | |
|---|---|
| [UserPasswordService](-user-password-service.md) | [jvm]<br>fun [UserPasswordService](-user-password-service.md)(@AutowiredpasswordResetRequestRepository: [PasswordResetRequestRepository](../../org.darkSolace.muse.user.repository/-password-reset-request-repository/index.md), @AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredmailService: [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [generateAndSendPasswordResetCode](generate-and-send-password-reset-code.md) | [jvm]<br>fun [generateAndSendPasswordResetCode](generate-and-send-password-reset-code.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md))<br>Generates a password reset code for a given user and sends it via email to the user. |
| [getUserByCode](get-user-by-code.md) | [jvm]<br>fun [getUserByCode](get-user-by-code.md)(passwordConfirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?<br>Finds a user identified by the password reset code |
| [updatePassword](update-password.md) | [jvm]<br>fun [updatePassword](update-password.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), newPassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Updates the password for a given user. Also generates a new salt. |

## Properties

| Name | Summary |
|---|---|
| [mailService](mail-service.md) | [jvm]<br>val [mailService](mail-service.md): [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md) |
| [passwordResetRequestRepository](password-reset-request-repository.md) | [jvm]<br>val [passwordResetRequestRepository](password-reset-request-repository.md): [PasswordResetRequestRepository](../../org.darkSolace.muse.user.repository/-password-reset-request-repository/index.md) |
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md) |
