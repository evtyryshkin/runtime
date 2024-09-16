pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Runtime"
include(":app")
include(":core")
include(":coreui")
include(":navigator")
include(":network")
include(":db")
include(":feature:settings")
include(":feature:tracker")
include(":feature:profile")
