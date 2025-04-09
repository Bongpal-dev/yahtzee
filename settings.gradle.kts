pluginManagement {
    repositories {
        includeBuild("build-src")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "yatzee"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(
    ":feature:main",
    ":feature:home",
    ":feature:play",
    ":feature:result"
)
include(
    ":core:navigation",
    ":core:designsystem",
    ":core:model",
    ":core:common",
    ":core:ui",
    ":core:domain",
    ":core:data",
    ":core:local"
)
include(":feature:scoreboard")
include(":core:resource")
include(":feature:guide")
