pluginManagement {
    repositories {
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

rootProject.name = "Ce Power Payment Book"
include(":app")
//include("../ComposeEntity/app")
//include("../ComposeEntity/composeentity_ksp")
include(":composeentity_ksp")
project(":composeentity_ksp").projectDir = file("../ComposeEntity/composeentity_ksp")
include(":composeentity_app")
project(":composeentity_app").projectDir = file("../ComposeEntity/app")

 