package org.dark_solace.muse.testConfig

import org.junit.jupiter.api.ClassDescriptor
import org.junit.jupiter.api.ClassOrderer
import org.junit.jupiter.api.ClassOrdererContext

class TestClassOrderer: ClassOrderer {
    override fun orderClasses(context: ClassOrdererContext?) {
        context?.classDescriptors?.sortBy { getClassOrder(it) }
    }

    private fun getClassOrder(classDescriptor: ClassDescriptor): Int {
        return when(classDescriptor.displayName) {
            "ElysianMuseApplicationTests" -> 1
            "UserModuleTests" -> 2
            else -> 99
        }
    }
}