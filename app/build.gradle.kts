plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.didong_foodapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.didong_foodapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "4.1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

    dependencies {

        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.constraintlayout)
        implementation(libs.lifecycle.livedata.ktx)
        implementation(libs.lifecycle.viewmodel.ktx)
        implementation(libs.navigation.fragment)
        implementation(libs.navigation.ui)
        implementation(libs.activity)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.database)
        implementation(libs.firebase.storage)
        implementation(libs.play.services.location)
        implementation(libs.play.services.maps)
        implementation(libs.rules)
        implementation(libs.fragment.testing)

        // Unit testing dependencies
        testImplementation(libs.junit)
        testImplementation(libs.core)
        testImplementation (libs.mockito.core)
        testImplementation("org.robolectric:robolectric:4.10.3")
        testImplementation("androidx.arch.core:core-testing:2.2.0")


        // Instrumentation testing dependencies
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
        androidTestImplementation (libs.mockito.core)
        androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    }