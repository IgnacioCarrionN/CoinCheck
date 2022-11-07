object Deps {

    object KotlinX {
        object Coroutines {
            private const val version = "1.6.4"
            const val core =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"

    }

    const val uuid = "com.benasher44:uuid:0.5.0"
    const val bigNum = "com.ionspin.kotlin:bignum:0.3.7"

    object SQLDelight {
        private const val version = "1.5.3"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:$version"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$version"
    }

    object Ktor {
        private const val ktorVersion = "2.1.2"
        const val core = "io.ktor:ktor-client-core:$ktorVersion"
        const val logging = "io.ktor:ktor-client-logging:$ktorVersion"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
        const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
        const val androidClient = "io.ktor:ktor-client-okhttp:$ktorVersion"
        const val iosClient = "io.ktor:ktor-client-darwin:$ktorVersion"
    }

    object Android {
        const val minSdk = 21
        const val compileSdk = 33
        const val targetSdk = 33
    }

    const val jUnit  = "junit:junit:4.13.2"

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.5.1"
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"

        object Compose {
            const val version = "1.3.0"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val material = "androidx.compose.material:material:$version"
            const val iconsCore = "androidx.compose.material:material-icons-core:$version"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val activity = "androidx.activity:activity-compose:1.6.1"
        }


        const val jUnitExt = "androidx.test.ext:junit:1.1.3"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"


    }

    object Google {
        const val material = "com.google.android.material:material:1.7.0"

        object Hilt {
            private const val version = "2.44"
            const val android = "com.google.dagger:hilt-android:$version"
            const val compiler = "com.google.dagger:hilt-compiler:$version"
        }
    }

    const val coil = "io.coil-kt:coil-compose:2.2.2"
}

