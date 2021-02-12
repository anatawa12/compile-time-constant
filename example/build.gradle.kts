buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath "com.anatawa12:compile-time-constant:1.0.1"
    }
}

plugins {
    id 'java'
    id 'org.jetbrains.kotlin.jvm'
    id 'application'
}

apply plugin: 'com.anatawa12.compile-time-constant'

group GROUP
version VERSION_NAME

sourceCompatibility = 1.8
mainClassName = "com.anatawa12.compileTimeConstant.example.MainKt"

repositories {
    mavenCentral()
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
}

createCompileTimeConstant {
    constantsClass = "com.anatawa12.compileTimeConstant.example.Constants"
    values version: VERSION_NAME
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
