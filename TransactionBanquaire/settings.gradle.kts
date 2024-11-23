rootProject.name = "TransactionBanquaire"
include(":app")

pluginManagement {
    repositories {
        google() // Ensure Google's repository is included for Android tools
        mavenCentral() // Ensure Maven Central is included for general dependencies
        gradlePluginPortal()  // Ensure this repository is set
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
