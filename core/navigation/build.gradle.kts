import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.library)
    alias(libs.plugins.yatzee.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.navigation")

dependencies {
    implementation(libs.kotlinx.serialization.json)
}

