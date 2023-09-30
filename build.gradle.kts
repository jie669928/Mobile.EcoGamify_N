// Top-level build file where you can add configuration options common to all sub-projects/modules.

//buildscript {
//    repositories {
//        // Make sure that you have the following two repositories
//        google()  // Google's Maven repository
//        mavenCentral()  // Maven Central repository
//
//    }
//    dependencies {
//        classpath("com.google.gms:google-services:4.4.0")
//    }
//}
buildscript {
    val agp_version by extra("8.1.1")
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
//
//buildscript {
//    repositories {
//        google() // Google's Maven repository
//        mavenCentral() // Maven Central repository
//    }
//    dependencies {
//        // Add the dependency for the Google services Gradle plugin
//        classpath("com.google.gms:google-services:4.3.13")
//    }
//}
//
//plugins {
//    id("com.android.application") version "7.0.0"
//    kotlin("android") version "1.5.21"
//    id("com.google.gms.google-services") // Apply the Google services plugin
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
//
//tasks {
//    register("clean", Delete::class) {
//        delete(rootProject.buildDir)
//    }
//}
