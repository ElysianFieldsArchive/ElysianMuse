plugins {
    kotlin("jvm")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("jacoco")
    id("org.jetbrains.dokka")
    id("maven-publish")

}

apply(plugin = "io.spring.dependency-management")

group = "org.darkSolace"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation(Spring.boot.data.jpa)
    implementation(Spring.boot.security)
    implementation(Spring.boot.web)
    implementation(Spring.boot.mail)
    implementation(Spring.boot.validation)
    implementation(libs.jjwt.api)

    runtimeOnly(kotlin("reflect"))
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    testImplementation(Spring.boot.test)
    testImplementation(Spring.security.spring_security_test)
    testImplementation(Testing.junit.jupiter)
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.junit.jupiter.engine)
    testImplementation(Testing.junit.jupiter.params)
    testImplementation(platform(Testing.junit.bom))
    testImplementation(libs.postgresql)
    testImplementation(libs.httpclient5)
    testImplementation(libs.greenmail.junit5)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(kotlin("test"))
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly(libs.org.postgresql.postgresql)
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport, tasks["copyDocs"])

}
kotlin {
    jvmToolchain(21)
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
        csv.required = false
    }
}

tasks.register("copyDocs") {
    dependsOn(tasks.dokkaGfm)
    delete("${projectDir}/../../docs/-elysian-muse")
    copy {
        from("${layout.buildDirectory.get()}/dokka/gfm")
        into("${layout.buildDirectory.get()}/../../docs/")
    }
}
