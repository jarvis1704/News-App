plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.biprangshu.newsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.biprangshu.newsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.navigation:navigation-compose:2.6.0")

    implementation ("com.google.dagger:hilt-android:2.45")
    kapt ("com.google.dagger:hilt-compiler:2.45")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Datastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //Compose Foundation
    implementation ("androidx.compose.foundation:foundation:1.4.3")

    //Accompanist
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.4-beta")

    //Paging 3
    val paging_version = "3.1.1"
    implementation ("androidx.paging:paging-runtime:$paging_version")
    implementation ("androidx.paging:paging-compose:3.2.0-rc01")

    //Room
    val room_version = "2.5.2"
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:2.5.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}