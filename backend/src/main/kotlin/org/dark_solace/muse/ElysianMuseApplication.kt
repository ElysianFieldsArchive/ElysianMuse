package org.dark_solace.muse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class ElysianMuseApplication

fun main(args: Array<String>) {
    runApplication<ElysianMuseApplication>(*args)
}