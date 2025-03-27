import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.library)
    alias(libs.plugins.ksp)
}

setNamespace("core.local")

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
}