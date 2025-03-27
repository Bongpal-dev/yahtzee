import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.feature)
}

setNamespace("feature.result")

dependencies {
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
}