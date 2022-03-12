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
            "UserServiceTests" -> 2
            "UserTagServiceTests" -> 3
            "UserRoleServiceTests" -> 4
            "AuthenticationServiceTests" -> 5
            "JwtUtilsTests" -> 6
            "UserDetailsServiceTests" -> 7
            "LastSeenServiceTests" -> 8
            "SuspensionServiceTests" -> 9
            else -> 50
        }
    }

    private fun getApiTestOrder(name: String): Int {
        return when (name) {
            "SuspensionServiceApiTests" -> 94
            "LastSeenApiTests" -> 95
            "UserControllerApiTests" -> 96
            "AuthControllerApiTests" -> 97
            "AuthControllerAccessTests" -> 98
            else -> 99
        }
    }
}
