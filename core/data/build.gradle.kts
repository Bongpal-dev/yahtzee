import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.library)
}

setNamespace("core.data")

dependencies {
    implementation(projects.core.domain)
}