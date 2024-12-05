plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "uk.co.xeiverse.ssh"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.co.xeiverse.ssh"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.2"

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
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.glide)

    implementation("org.apache.httpcomponents.core5:httpcore5:5.3.1")
    implementation("org.apache.httpcomponents.client5:httpclient5-fluent:5.4.1")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation("javax.persistence:javax.persistence-api:2.2")
    compileOnly("org.projectlombok:lombok:1.18.30")






}