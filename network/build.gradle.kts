plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "ru.tyryshkin.network"
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
    implementation(project(":core"))

    implementation(libs.retrofit)
    implementation(libs.okHttpClient)
    implementation(libs.converter.moshi)
    implementation(libs.serialization.json)
    implementation(libs.logging.interceptor)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
