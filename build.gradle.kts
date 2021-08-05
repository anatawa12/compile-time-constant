plugins {
    id("java")
    kotlin("jvm") version "1.4.30"
    id("com.gradle.plugin-publish") version "0.12.0"
    `java-gradle-plugin`
    `maven-publish`
    signing
}

group = "com.anatawa12"
@Suppress("UnstableApiUsage")
version = providers.gradleProperty("version").forUseAtConfigurationTime().get()

java {
    sourceCompatibility = JavaVersion.VERSION_1_6
    @Suppress("UnstableApiUsage")
    withSourcesJar()
    @Suppress("UnstableApiUsage")
    withJavadocJar()
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

pluginBundle {
    website = "https://github.com/anatawa12/compile-time-constant"
    vcsUrl = "https://github.com/anatawa12/compile-time-constant"
    tags = listOf("buildconstants", "constants")
}

publishing.publications.create<MavenPublication>("maven") {
    from(components["java"])
    pom {
        name.set("Compile Time Constant")
        description.set("A plugin to create constants class from gradle.")
        url.set("https://github.com/anatawa12/compile-time-constant#readme")
        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }
        developers {
            developer {
                id.set("anatawa12")
                name.set("anatawa12")
                email.set("anatawa12@icloud.com")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/anatawa12/compile-time-constant.git")
            developerConnection.set("scm:git:ssh://github.com:anatawa12/compile-time-constant.git")
            url.set("https://github.com/anatawa12/compile-time-constant/tree/master")
        }
    }
}

fun isReleaseBuild() = !version.toString().contains("SNAPSHOT")

fun getReleaseRepositoryUrl(): String {
    return project.findProperty("RELEASE_REPOSITORY_URL")?.toString()
        ?: "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
}

fun getSnapshotRepositoryUrl(): String {
    return project.findProperty("SNAPSHOT_REPOSITORY_URL")?.toString()
        ?: "https://oss.sonatype.org/content/repositories/snapshots/"
}

signing {
    publishing.publications.forEach { publication ->
        sign(publication)
    }
}

publishing {
    repositories {
        maven {
            name = "mavenCentral"
            url = uri(if (isReleaseBuild()) getReleaseRepositoryUrl() else getSnapshotRepositoryUrl())

            credentials {
                username = project.findProperty("com.anatawa12.sonatype.username")?.toString() ?: ""
                password = project.findProperty("com.anatawa12.sonatype.passeord")?.toString() ?: ""
            }
        }
    }
}

tasks.withType<PublishToMavenRepository>().configureEach {
    onlyIf {
        if (repository.name == "mavenCentral") {
            publication.name != "compile-time-constantPluginMarkerMaven"
                    && publication.name != "pluginMaven"
        } else {
            true
        }
    }
}
