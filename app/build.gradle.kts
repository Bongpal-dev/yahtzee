plugins {
    alias(libs.plugins.yatzee.android.application)
}

android{
    namespace = "com.bongpal.yatzee.app"
}

dependencies {
    implementation(projects.feature.main)
}