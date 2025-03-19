import com.bongpal.yatzee.configurationCoroutineAndroid
import com.bongpal.yatzee.configureHiltAndroid

plugins {
    id("yatzee.android.compose")
}

configureHiltAndroid()
configurationCoroutineAndroid()