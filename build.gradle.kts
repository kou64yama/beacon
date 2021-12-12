import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    configurations.classpath {
        resolutionStrategy.activateDependencyLocking()
    }
    repositories {
        jcenter()
    }
}

plugins {
    application
    jacoco
    kotlin("jvm") version "1.6.0"
    kotlin("kapt") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("io.gitlab.arturbosch.detekt") version "1.14.2"
}

dependencyLocking {
    lockAllConfigurations()
}

group = "works.nobushi"
version = "0.1.0-SNAPSHOT"

repositories {
    jcenter()
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
    testImplementation("com.ninja-squad:springmockk:2.0.3")
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
            xml.isEnabled = System.getenv("CI") == "true"
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
