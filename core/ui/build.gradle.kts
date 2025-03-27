import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.library)
    alias(libs.plugins.yatzee.android.compose)
}

setNamespace("core.ui")

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)

    implementation(libs.coil.compose)
}