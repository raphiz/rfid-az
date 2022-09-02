plugins {
    kotlin("multiplatform") version "1.7.10"
}

group = "li.raphael.rfid-az"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    // For further config, see https://github.com/jmfayard/kotlin-cli-starter/blob/5201ee91122b4572d40167e7fbfae2f341ce5dfb/build.gradle.kts
    linuxX64("linuxX64") {
        compilations.getByName("main") {
            cinterops {
                val input by creating{
                    packageName("linux.input")
                }
            }
        }
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.2.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting
        arrayOf("linuxX64").forEach { targetName ->
            getByName("${targetName}Main").dependsOn(commonMain)
            getByName("${targetName}Test").dependsOn(commonTest)
        }
    }
}
