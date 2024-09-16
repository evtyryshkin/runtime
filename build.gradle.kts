plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.ktlintPlugin) apply true
    alias(libs.plugins.secretsPlugins) apply false
    alias(libs.plugins.ksp) apply false
}

buildscript {
    extra.apply {
        with(libs.versions) {
            set("compileSdk", compileSdk.get().toInt())
            set("minSdk", minSdk.get().toInt())
            set("targetSdk", targetSdk.get().toInt())
        }
    }

    dependencies {
        classpath(libs.ktlint)
    }
}

allprojects {
    val libs = rootProject.libs

    apply(plugin = libs.plugins.ktlintPlugin.get().pluginId)
    apply(plugin = libs.plugins.ksp.get().pluginId)

    ktlint {
        version.set(libs.versions.ktlint.version)
    }
}
