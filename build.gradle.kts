plugins {
    id("java")
    kotlin("jvm") version "1.4.30"
    `java-gradle-plugin`
}

apply(from = "https://gist.githubusercontent.com/anatawa12/75cb9a093bc93ed473a7ca2ac489eaf9/raw/aec03f39610fa231acfbb000855a337d63d59510/gradle-mvn-push.gradle")

group = project.property("GROUP")!!
version = project.property("VERSION_NAME")!!

java {
    sourceCompatibility = JavaVersion.VERSION_1_6
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("org.ow2.asm:asm:7.1")
}

gradlePlugin {
    plugins {
        register("compile-time-constant") {
            displayName = "Compile Time Constant"
            description = "A plugin to create constants class from gradle."
            id = "com.anatawa12.compile-time-constant"
            implementationClass = "com.anatawa12.compileTimeConstant.PluginMain"
        }
    }
}
