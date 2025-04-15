import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.yatzee.android.application)
}

android {
    namespace = "com.bongpal.yatzee.app"

    defaultConfig {
        applicationId = "com.bongpal.yatzee.app"
        targetSdk = 34
        versionCode = 2
        versionName = "0.1.1"
    }

    signingConfigs {
        val localProperties = Properties()

        localProperties.load(FileInputStream(rootProject.file("local.properties")))
        create("release") {
            storeFile = file(localProperties.getProperty("RELEASE_STORE_FILE").toString())
            storePassword = localProperties.getProperty("RELEASE_STORE_PASSWORD").toString()
            keyAlias = localProperties.getProperty("RELEASE_KEY_ALIAS").toString()
            keyPassword = localProperties.getProperty("RELEASE_KEY_PASSWORD").toString()
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.core.data)
}