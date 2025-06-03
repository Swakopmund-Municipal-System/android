//import androidx.compose.ui.layout.layout
//import androidx.datastore.core.use
//import androidx.glance.appwidget.compose
//import androidx.navigation.compose.navigation
import java.nio.charset.StandardCharsets
import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.reader(StandardCharsets.UTF_8).use { reader ->
        localProperties.load(reader)
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.swakopmundapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.swakopmundapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "SHARED_API_KEY",
                "\"${localProperties.getProperty("SHARED_API_KEY", "YOUR_DEFAULT_KEY_IF_NOT_FOUND")}\""
            )
            buildConfigField(
                "String",
                "SHARED_AUTH_TOKEN",
                "\"${localProperties.getProperty("SHARED_AUTH_TOKEN", "YOUR_DEFAULT_TOKEN_IF_NOT_FOUND")}\""
            )
        }
        debug {
            buildConfigField(
                "String",
                "SHARED_API_KEY",
                "\"${localProperties.getProperty("SHARED_API_KEY", "YOUR_DEFAULT_KEY_IF_NOT_FOUND_DEBUG")}\""
            )
            buildConfigField(
                "String",
                "SHARED_AUTH_TOKEN",
                "\"${localProperties.getProperty("SHARED_AUTH_TOKEN", "YOUR_DEFAULT_TOKEN_IF_NOT_FOUND_DEBUG")}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":service"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.gson)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava2)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.extensions)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.coil.compose)
    implementation(libs.androidx.constraintlayout.compose.android)
    implementation(libs.androidx.runtime.livedata)
    implementation("com.mapbox.maps:android:11.12.2")
    implementation("com.mapbox.extension:maps-compose:11.12.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}