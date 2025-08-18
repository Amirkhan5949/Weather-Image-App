plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.example.weatherimageapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.weatherimageapp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //dagger_hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.compiler)

    //activity_ktx
    implementation(libs.androidx.activity.ktx)

    //fragment_ktx
    implementation(libs.androidx.fragment.ktx)

    //viewmodel
    implementation(libs.lifecycle.viewmodel.ktx)

    // liveData
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)

    //picasso
    implementation(libs.picasso)
}