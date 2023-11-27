import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    testImplementation(platform("io.qameta.allure:allure-bom:2.24.0"))
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation("org.jetbrains.exposed:exposed-core:0.44.1")
    testImplementation("org.jetbrains.exposed:exposed-crypt:0.44.1")
    testImplementation("org.jetbrains.exposed:exposed-dao:0.44.1")
    testImplementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    testImplementation("org.xerial:sqlite-jdbc:3.44.0.0")
    agent("org.aspectj:aspectjweaver:1.9.20.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
tasks.test {
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}