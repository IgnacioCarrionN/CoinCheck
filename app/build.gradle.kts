plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "dev.carrion.coincheck"
    compileSdk = 33

    defaultConfig {
        applicationId = "dev.carrion.coincheck"
        minSdk = 21
        targetSdk = 33
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {
    implementation(project(":common:coin-list"))
    implementation(project(":common:remote"))
    implementation(project(":common:database"))
    implementation(project(":common:architecture"))

    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.Google.material)

    implementation(Deps.Google.Hilt.android)
    kapt(Deps.Google.Hilt.compiler)

    implementation(Deps.AndroidX.Compose.ui)
    implementation(Deps.AndroidX.Compose.uiTooling)
    implementation(Deps.AndroidX.Compose.foundation)
    implementation(Deps.AndroidX.Compose.material)
    implementation(Deps.AndroidX.Compose.iconsCore)
    implementation(Deps.AndroidX.Compose.iconsExtended)
    implementation(Deps.AndroidX.Compose.activity)
    implementation(Deps.AndroidX.lifecycleKtx)

    implementation(Deps.coil)

    testImplementation(Deps.jUnit)

    androidTestImplementation(Deps.AndroidX.jUnitExt)
    androidTestImplementation(Deps.AndroidX.espressoCore)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

apply {
    from("${rootProject.rootDir}/tools/detekt.gradle")
}

tasks.check {
    dependsOn(tasks.detekt)
}

tasks.register("generateVersionTxt") {
    doLast {
        val versionName = android.defaultConfig.versionName ?: "Unknown version"
        val versionCode = android.defaultConfig.versionCode ?: "Unknown version code"
        file("./version.txt").writeText("Version Name: $versionName - Version Code: $versionCode")
    }
}