import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.build.konfig)
    alias(libs.plugins.detekt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

val localPropertiesFile: File = rootProject.file("local.properties")
val localProperties = Properties().apply {
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

buildkonfig {
    packageName = "francisco.simon.projectkmp"
    objectName = "StepikAppSecrets"

    // Fallback to environment variables for CI
    val clientId =
        (localProperties["stepik.client.id"] ?: System.getenv("STEPIK_CLIENT_ID")) as String
    val clientSecret =
        (localProperties["stepik.client.secret"] ?: System.getenv("STEPIK_CLIENT_SECRET")) as String

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "STEPIK_CLIENT_ID", clientId)
        buildConfigField(FieldSpec.Type.STRING, "STEPIK_CLIENT_SECRET", clientSecret)
    }
}

detekt {
    buildUponDefaultConfig = true
    autoCorrect = true
    source.setFrom(
        "src/commonMain/kotlin",
        "src/androidMain/kotlin",
        "src/iosMain/kotlin"
    )
    config.setFrom(file("../config/detekt/detekt.yml"))
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.android)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.android)
            implementation(project.dependencies.platform(libs.firebase.android.bom))
            implementation(libs.firebase.common)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.navigation)
            implementation(libs.coil.compose)
            implementation(libs.coil.ktor)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.napier)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.ktor.auth)
            implementation(libs.data.store)
            implementation(libs.data.store.preferences)
            implementation(libs.kotlinx.datetime)
            api(libs.gitlive.firebase.kotlin.crashlytics)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.ios)
        }
    }
}

android {
    namespace = "francisco.simon.projectkmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "francisco.simon.projectkmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    signingConfigs {
        val keyStoreFile = file("keystore/keystore_config")
        val keyStore = Properties().apply {
            if (keyStoreFile.exists()) {
                load(keyStoreFile.inputStream())
            }
        }

        register("release").configure {
            if (keyStoreFile.exists()) {
                storeFile = file("${keyStore["storeFile"]}")
                storePassword = "${keyStore["storePassword"]}"
                keyAlias = "${keyStore["keyAlias"]}"
                keyPassword = "${keyStore["keyPassword"]}"
            } else {
                storeFile = file("keystore/stepik_keystore")
                storePassword = System.getenv("STEPIK_STORE_PASSWORD")
                keyAlias = System.getenv("STEPIK_KEY_ALIAS")
                keyPassword = System.getenv("STEPIK_KEY_PASSWORD")
            }
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isDebuggable = true
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.compose.uiTooling)
}

tasks.register<Copy>("copyGitHooks") {
    description = "Copies the git hooks to the .git folder."
    group = "git hooks"
    from("$rootDir/scripts")
    into("$rootDir/.git/hooks/")
}

tasks.register<Exec>("installGitHooks") {
    description = "Installs the git hooks."
    group = "git hooks"
    workingDir = rootDir
    commandLine = listOf("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    doLast {
        logger.info("Git hook installed successfully.")
    }
}

tasks.named("preBuild") {
    dependsOn("installGitHooks")
}
