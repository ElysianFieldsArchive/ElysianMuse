//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.security.controller](../index.md)/[AuthController](index.md)/[registerUser](register-user.md)

# registerUser

[jvm]\

@PostMapping(value = [&quot;/signup&quot;])

fun [registerUser](register-user.md)(@Valid@RequestBodysignUpRequest: [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md)): ResponseEntity&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt;

Checks a transmitted [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md) and creates a user if possible. Listens on /api/auth/signup.

```kotlin
'{ "username": "test", "password": "123456", "email": "test@example.com" }'
localhost:8000/api/auth/signup`
```

#### Return

HTTP-Status 200 OK if user was created successfully or 400 BAD REQUEST if an error occurred

#### Parameters

jvm

| | |
|---|---|
| signUpRequest | a [SignUpRequest](../../org.darkSolace.muse.security.model/-sign-up-request/index.md) containing username, password and email address |