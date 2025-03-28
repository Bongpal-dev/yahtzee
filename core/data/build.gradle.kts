import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.library)
}

setNamespace("core.data")

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.local)
    implementation(projects.core.resource)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.compose.ui)
}