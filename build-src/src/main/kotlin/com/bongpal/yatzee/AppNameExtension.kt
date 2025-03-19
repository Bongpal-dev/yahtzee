package com.bongpal.yatzee

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.bongpal.$name"
    }
}