plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "2.0.0-1.0.24"

    kotlin("plugin.serialization")
}

android {
    namespace = "io.github.sergeyboboshko.cereport"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.sergeyboboshko.cereport"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    val ceVersion = "1.0.27"
    implementation("io.github.sergeyboboshko:composeentity_ksp:$ceVersion")
    ksp("io.github.sergeyboboshko:composeentity_ksp:$ceVersion")
    implementation("io.github.sergeyboboshko:composeentity:$ceVersion")

//    implementation (files("libs/composeentity_ksp.jar"))
//    ksp(files("libs/composeentity_ksp.jar"))
//    implementation(files("libs/composeentity-1.0.10-sources.jar"))
//    implementation (project(":composeentity_ksp"))
//    ksp(project(":composeentity_ksp"))
//    implementation(project(":composeentity_app"))

    implementation("androidx.navigation:navigation-compose:2.8.3")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
}

// turns on kapt
kapt {
    correctErrorTypes = true
    useBuildCache = true
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

// ksp set
ksp {
    arg("ksp.allow.all.target.configuration", "false")  //provide configuration only for main source set
}