import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.library)
}

setNamespace("core.domain")

dependencies {
    api(projects.core.model)
    api(projects.core.common)

    implementation(libs.androidx.paging.runtime)
}