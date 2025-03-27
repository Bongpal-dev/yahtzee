import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.feature)
}

setNamespace("feature.scoreboard")

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}