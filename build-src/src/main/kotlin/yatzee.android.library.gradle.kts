import com.bongpal.yatzee.configureHiltAndroid
import com.bongpal.yatzee.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureHiltAndroid()