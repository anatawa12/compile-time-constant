plugins {
    id 'java'
    id 'org.jetbrains.kotlin.jvm' version '1.3.21'
    id "org.jetbrains.kotlin.plugin.allopen" version "1.3.21"
}

apply from: "https://gist.githubusercontent.com/anatawa12/75cb9a093bc93ed473a7ca2ac489eaf9/raw/aec03f39610fa231acfbb000855a337d63d59510/gradle-mvn-push.gradle"

group GROUP
version VERSION_NAME

sourceCompatibility = 1.8

sourceSets {

}

repositories {
    mavenCentral()
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    implementation gradleApi()
    implementation localGroovy()
    implementation group: 'org.ow2.asm', name: 'asm', version: '7.1'
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

allOpen {
    annotation("org.gradle.api.tasks.CacheableTask")
}

