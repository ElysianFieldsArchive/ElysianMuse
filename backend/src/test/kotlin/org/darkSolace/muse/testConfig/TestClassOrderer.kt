package org.darkSolace.muse.testConfig

import org.junit.jupiter.api.ClassDescriptor
import org.junit.jupiter.api.ClassOrderer
import org.junit.jupiter.api.ClassOrdererContext

class TestClassOrderer: ClassOrderer {
    override fun orderClasses(context: ClassOrdererContext?) {
        context?.classDescriptors?.sortBy { getClassOrder(it) }
    }

    private fun getClassOrder(classDescriptor: ClassDescriptor): Int {
        return when (classDescriptor.displayName) {
            "ElysianMuseApplicationTests" -> 1
            "UserServiceTests" -> 2
            "UserTagServiceTests" -> 3
            "UserRoleServiceTests" -> 4
            "AuthenticationServiceTests" -> 5
            "JwtUtilsTests" -> 6
            "UserDetailsServiceTests" -> 7

            //API Tests
            "UserControllerApiTests" -> 96
            "AuthControllerApiTests" -> 97
            "AuthControllerAccessTests" -> 98
            else -> 99
        }
    }
}
