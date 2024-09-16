android {
    namespace = "ru.tyryshkin.profile"
}

dependencies {
    implementation(project(":feature:tracker")) // TODO убрать эту зависимость (так сделано было, чтобы показать подробную информацию о пробежке), создать уникальный экран RaceDetail

    implementation(libs.maps)
    implementation(libs.maps.compose)
}
