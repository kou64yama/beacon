import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    configurations.classpath {
        resolutionStrategy.activateDependencyLocking()
    }
    repositories {
        mavenCentral()
    }
}

plugins {
    application
    jacoco
    kotlin("jvm") version "1.6.0"
    kotlin("kapt") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

dependencyLocking {
    lockAllConfigurations()
}

group = "works.nobushi"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

springBoot {
    buildInfo()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito", module = "mockito-core")
    }
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("io.projectreactor:reactor-test")
}

tasks {
    bootJar {
        archiveVersion.set("")
    }

    test {
        finalizedBy(jacocoTestReport)
        useJUnitPlatform()
    }

    jacocoTestReport {
        reports {
            xml.required.set(System.getenv("CI") == "true")
        }
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                element = "METHOD"
                limit {
                    minimum = 1.toBigDecimal()
                    excludes = listOf("works.nobushi.beacon.ApplicationKt.main(java.lang.String[])")
                }
            }
        }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}
