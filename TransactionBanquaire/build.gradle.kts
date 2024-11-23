plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.apollo) apply false
}

buildscript {
    repositories {
        google() // Ensure Google's repository is included for Android tools
        mavenCentral() // Ensure Maven Central is included for general dependencies
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0")  // Android Gradle Plugin
        classpath("com.apollographql.apollo3:apollo-gradle-plugin:3.8.2") // Apollo plugin
    }
}

allprojects {

}