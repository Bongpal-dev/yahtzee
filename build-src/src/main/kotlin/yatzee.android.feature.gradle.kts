import com.bongpal.yatzee.configurationCoroutineAndroid
import com.bongpal.yatzee.configureHiltAndroid
import com.bongpal.yatzee.implementation

plugins {
    id("yatzee.android.library")
    id("yatzee.android.compose")
}

configureHiltAndroid()
configurationCoroutineAndroid()

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
}