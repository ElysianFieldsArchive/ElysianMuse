//[elysianmuse](../../index.md)/[org.darkSolace.muse.user.controller](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [UserController](-user-controller/index.md) | [jvm]<br>@RestController<br>@RequestMapping(value = [&quot;/api/user&quot;])<br>class [UserController](-user-controller/index.md)(@Autowiredval userService: [UserService](../org.darkSolace.muse.user.service/-user-service/index.md), @Autowiredval userRoleService: [UserRoleService](../org.darkSolace.muse.user.service/-user-role-service/index.md), @Autowiredval userTagService: [UserTagService](../org.darkSolace.muse.user.service/-user-tag-service/index.md), @Autowiredval suspensionService: [SuspensionService](../org.darkSolace.muse.user.service/-suspension-service/index.md))<br>RestController defining endpoints regarding all user activity Subject to change |
| [UserPasswordController](-user-password-controller/index.md) | [jvm]<br>@RestController<br>@RequestMapping(value = [&quot;/api/user&quot;])<br>class [UserPasswordController](-user-password-controller/index.md)(@Autowiredval userService: [UserService](../org.darkSolace.muse.user.service/-user-service/index.md), @Autowiredval userPasswordService: [UserPasswordService](../org.darkSolace.muse.user.service/-user-password-service/index.md))<br>RestController defining endpoints regarding all password reset activities |
