
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
}

// Load local.properties
val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

// Get the API key
val newsApiKey: String = localProperties.getProperty("newsApiKey")

android {
    namespace = "com.shivamsingh.verinews"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shivamsingh.verinews"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Use the key from local.properties
        buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")
    }

    buildFeatures{
        buildConfig=true
        viewBinding=true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation (libs.glide)
    // build.gradle
    implementation (libs.logging.interceptor)

    annotationProcessor (libs.compiler)
    // Retrofit core
    implementation (libs.retrofit2.retrofit)
    // Gson converter (for JSON to Kotlin/Java object mapping)
    implementation (libs.converter.gson)
    implementation (libs.androidx.multidex)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.androidx.swiperefreshlayout)
}