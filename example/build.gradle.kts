import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.anatawa12.compileTimeConstant.CreateConstantsTask

plugins {
    id("java")
    id("com.anatawa12.compile-time-constant") version "1.0.3"
    id("org.jetbrains.kotlin.jvm") version "1.4.30"
    id("application")
}

group = property("GROUP")!!
version = property("VERSION_NAME")!!

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "com.anatawa12.compileTimeConstant.example.MainKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

val createCompileTimeConstant by tasks.getting(CreateConstantsTask::class) {
    constantsClass = "com.anatawa12.compileTimeConstant.example.Constants"
    values(mapOf("version" to project.property("VERSION_NAME")!! as String))
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
}
val compileTestKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
}
