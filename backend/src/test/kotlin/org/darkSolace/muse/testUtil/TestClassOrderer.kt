package org.darkSolace.muse.testUtil

import org.junit.jupiter.api.ClassOrderer
import org.junit.jupiter.api.ClassOrdererContext

class TestClassOrderer : ClassOrderer {
    override fun orderClasses(context: ClassOrdererContext?) {
        context?.classDescriptors?.sortBy {
            if (it.displayName == "ElysianMuseApplicationTests")
                "A"
            else
                it.displayName
        }
    }
}
