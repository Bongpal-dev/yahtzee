import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.feature)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    setNamespace("feature.main")

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.play)
    implementation(projects.feature.result)
    implementation(projects.feature.scoreboard)
}