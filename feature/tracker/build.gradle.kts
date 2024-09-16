android {
    namespace = "ru.tyryshkin.tracker"
}

dependencies {
    implementation(project(":feature:settings"))

    implementation(libs.maps)
    implementation(libs.maps.compose)
    implementation(libs.location)
}
