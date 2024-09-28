import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.secretsPlugins)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltPlugin)
    id("maven-publish")
}

val versionProps = Properties()
versionProps.load(FileInputStream(rootProject.file("versions.properties")))

android {
    namespace = "ru.tyryshkin.runtime"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "ru.tyryshkin.runtime"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionName = versionProps.getProperty("versionName")
        versionCode = versionProps.getProperty("versionCode").toInt()

        testInstrumentationRunner = "ru.tyryshkin.runtime.utils.TestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
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
        jvmTarget = libs.versions.java.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    publishing {
        singleVariant("debug") {
            publishApk()
        }
    }
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
    ignoreList.add("keyToIgnore")
    ignoreList.add("sdk.*")
}

dependencies {
    implementation(project(":navigator"))
    implementation(project(":coreui"))
    implementation(project(":core"))
    implementation(project(":db"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:tracker"))
    implementation(project(":feature:profile"))

    implementation(platform(libs.compose.bom))
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.navigation.compose)
    implementation(libs.timber)
    coreLibraryDesugaring(libs.desugar)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    implementation(libs.runner)
    implementation(libs.test.core.ktx)
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.tyryshkin.runtime"
            artifactId = "app"
            version = "1.1"

            afterEvaluate {
                from(components["debug"])
            }
        }
    }
}
