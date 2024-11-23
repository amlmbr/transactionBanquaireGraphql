plugins {
    id("com.android.application")
    id("com.apollographql.apollo3") version "3.8.2"
}

android {
    namespace = "com.ensa.transactionbanquaire"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ensa.transactionbanquaire"
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
        sourceCompatibility =JavaVersion.VERSION_17
                targetCompatibility= JavaVersion.VERSION_17
    }
}

apollo {
    service("service") {
        packageName.set("com.ensa.graphql")
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Existing dependencies...
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")





    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Apollo RxJava3 Support
    implementation ("com.apollographql.apollo3:apollo-rx3-support:3.8.2")  // Remplacez x.x par votre version

    // RxJava3 Dependencies
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")




    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")

         // Assurez-vous d'utiliser la version la plus récente
        implementation ("com.squareup.okhttp3:okhttp:4.9.3") // Assurez-vous que vous avez OkHttp en version 4.x

    implementation ("com.apollographql.apollo3:apollo-api:3.8.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


    // Apollo Client pour Android
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson pour la sérialisation JSON (si nécessaire)
    implementation ("com.google.code.gson:gson:2.8.9")
}