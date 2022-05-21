package org.darkSolace.muse.testUtil

import org.junit.jupiter.api.ClassDescriptor
import org.junit.jupiter.api.ClassOrderer
import org.junit.jupiter.api.ClassOrdererContext

class TestClassOrderer : ClassOrderer {
    override fun orderClasses(context: ClassOrdererContext?) {
        context?.classDescriptors?.sortBy { getClassOrder(it) }
    }

    private fun getClassOrder(classDescriptor: ClassDescriptor): Int {
        // run api tests after service tests
        return when {
            classDescriptor.displayName.endsWith("ApiTests") -> getApiTestOrder(classDescriptor.displayName)
            else -> getServiceTestOrder(classDescriptor.displayName)
        }
    }

    private fun getServiceTestOrder(name: String): Int {
        return when (name) {
            "ElysianMuseApplicationTests" -> 1
            "MailServiceTests" -> 2
            "UserServiceTests" -> 3
            "UserTagServiceTests" -> 4
            "UserRoleServiceTests" -> 5
            "AuthenticationServiceTests" -> 6
            "JwtUtilsTests" -> 7
            "UserDetailsServiceTests" -> 8
            "LastSeenServiceTests" -> 9
            "SuspensionServiceTests" -> 10
            "UserPasswordServiceTests" -> 10
            else -> 50
        }
    }

    private fun getApiTestOrder(name: String): Int {
        return when (name) {
            "MailApiTests" -> 92
            "UserPasswordApiTests" -> 93
            "SuspensionServiceApiTests" -> 94
            "LastSeenApiTests" -> 95
            "UserControllerApiTests" -> 96
            "AuthControllerApiTests" -> 97
            "AuthControllerAccessTests" -> 98
            else -> 99
        }
    }
}
