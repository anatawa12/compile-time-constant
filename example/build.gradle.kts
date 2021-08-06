import com.anatawa12.compileTimeConstant.CreateConstantsTask

plugins {
    id("java")
    id("com.anatawa12.compile-time-constant") version "1.0.4"
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
}

val createCompileTimeConstant by tasks.getting(CreateConstantsTask::class) {
    constantsClass = "com.anatawa12.compileTimeConstant.example.Constants"
    values(mapOf("version" to project.property("VERSION_NAME")!! as String))
}
