import com.bongpal.yatzee.setNamespace

plugins {
    id("yatzee.android.library")
    id("yatzee.android.compose")
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.navigation")

dependencies {
    implementation(libs.kotlinx.serialization.json)
}

