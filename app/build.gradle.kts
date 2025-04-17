plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("jacoco")
}

android {
    namespace = "com.christianalexandre.mlchallengeandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.christianalexandre.mlchallengeandroid"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.glide)
    kapt(libs.compiler)

    implementation(libs.org.jacoco.core)

    // Tests dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.idling.resource)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.contrib)
}

kapt {
    correctErrorTypes = true
}

tasks.register<JacocoReport>("jacocoFullReport") {
    val fileFilter =
        listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "android/**/*.*",
        )

    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")

    val javaClasses =
        fileTree("$buildDir/intermediates/javac/debug/classes") {
            exclude(fileFilter)
        }
    val kotlinClasses =
        fileTree("$buildDir/tmp/kotlin-classes/debug") {
            exclude(fileFilter)
        }

    classDirectories.setFrom(files(javaClasses, kotlinClasses))
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))

    val emulator = "Pixel_7_Android_14(AVD) - 14" // It's necessary update with your AVD folder name
    executionData.setFrom(
        files(
            "$buildDir/jacoco/testDebugUnitTest.exec",
            "$buildDir/outputs/code_coverage/debugAndroidTest/connected/$emulator/coverage.ec",
        ),
    )

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}