import com.bongpal.yatzee.setNamespace

plugins {
    alias(libs.plugins.yatzee.android.feature)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    setNamespace("feature.main")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"

    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.play)
}