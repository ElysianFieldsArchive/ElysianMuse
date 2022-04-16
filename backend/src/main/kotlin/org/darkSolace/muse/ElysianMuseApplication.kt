package org.darkSolace.muse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class ElysianMuseApplication

const val MINIMUM_KEY_LENGTH = 64
const val BITS_IN_BYTE = 8

/**
 * Starts Elysian Muse
 */
fun main() {
    secretCheck()
    runApplication<ElysianMuseApplication>()
}

/**
 * Checks whether the chosen JWT Secret is large enough. If not the program is closed with an error.
 */
fun secretCheck() {
    ElysianMuseApplication::class.java.classLoader.getResource("application.properties")
        ?.readText()
        ?.split("\n")
        ?.forEach { line ->
            if (line.startsWith("muse.app.jwtSecret")) {
                val secretSize = line.split("=").last().trim('\r').toByteArray().size
                if (secretSize < MINIMUM_KEY_LENGTH) {
                    error("muse.app.jwtSecret is to short, must by 512 bits, is ${secretSize * BITS_IN_BYTE}")
                }
            }
        }
}
