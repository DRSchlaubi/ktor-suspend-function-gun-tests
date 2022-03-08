plugins {
    kotlin("multiplatform") version "1.6.10"
}

group = "dev.schlaubi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
//        browser()
        nodejs()
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }

        commonMain {
            dependencies {
                implementation(project.dependencies.platform("io.ktor:ktor-bom:2.0.0-beta-1"))
                implementation("io.ktor:ktor-client-core")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0-native-mt")
            }
        }
        getByName("jvmMain") {
            dependencies {
                implementation("io.ktor:ktor-client-cio")
            }
        }
        getByName("jsMain") {
            dependencies {
                implementation("io.ktor:ktor-client-js")
            }
        }
        getByName("nativeMain") {
            dependencies {
                implementation("io.ktor:ktor-client-curl")
            }
        }
    }
}
