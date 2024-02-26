plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.gradlePlugin)
}

android {
    namespace = "com.em.online_market"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.em.online_market"
        minSdk = 24
        targetSdk = 34
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)


    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)


    implementation(project(":online_market_feature:authorization"))

    implementation(project(":online_market_api"))
    implementation(project(":online_market_data"))

    implementation(project(":online_market_core:theme"))
    implementation(project(":online_market_core:common-impl"))
    implementation(project(":online_market_core:presentation"))

}