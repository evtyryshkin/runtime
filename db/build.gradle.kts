plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.tyryshkin.db"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {
    api(libs.room.runtime)
    api(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.maps)
    implementation(libs.serialization.json)
}
