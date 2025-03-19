package com.bongpal.yatzee

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configurationCoroutineAndroid() {
    configurationCoroutineKotlin()

    dependencies {
        implementation(libs.coroutines.android)
    }
}

internal fun Project.configurationCoroutineKotlin() {
    dependencies{
        implementation(libs.coroutines.core)
    }
}