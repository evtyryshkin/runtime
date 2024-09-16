plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "ru.tyryshkin.core"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.get()
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.viewmodel)
    implementation(libs.material3)
    implementation(libs.accomponnist.permissions)
    implementation(libs.location)
    implementation(libs.timber)
    implementation(libs.maps)
    coreLibraryDesugaring(libs.desugar)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ui.test.junit4)
}
