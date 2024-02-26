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

rootProject.name = "Online Market"
include(":app")
include(":online_market_feature:authorization")

include(":online_market_api")
include(":online_market_data")
include(":online_market_core:common-impl")
include(":online_market_core:theme")
include(":online_market_core:common")
include(":online_market_core:presentation")


