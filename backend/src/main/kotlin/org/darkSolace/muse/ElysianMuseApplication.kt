package org.darkSolace.muse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class ElysianMuseApplication

const val MINIMUM_KEY_LENGTH = 64
const val BITS_IN_BYTE = 8

fun main() {
    secretCheck()
    runApplication<ElysianMuseApplication>()
}

fun secretCheck() {
    ElysianMuseApplication::class.java.classLoader.getResource("application.properties")
        ?.readText()
        ?.split("\n")
        ?.forEach { line ->
            if (line.startsWith("muse.app.jwtSecret")) {
                val secretSize = line.split("=").last().trim('\r').toByteArray().size
                if (secretSize < MINIMUM_KEY_LENGTH) {
                    error("jwtSecret is to short, must by 512 bits, is ${secretSize * BITS_IN_BYTE}")
                }
            }
        }
}

