import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.21"
}

apply(from = "https://gist.githubusercontent.com/anatawa12/75cb9a093bc93ed473a7ca2ac489eaf9/raw/aec03f39610fa231acfbb000855a337d63d59510/gradle-mvn-push.gradle")

group = project.property("GROUP")!!
version = project.property("VERSION_NAME")!!

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {

}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("org.ow2.asm:asm:7.1")
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
}
val compileTestKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
}

allOpen {
    annotation("org.gradle.api.tasks.CacheableTask")
}

