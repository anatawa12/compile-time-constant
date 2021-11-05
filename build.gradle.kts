plugins {
    id("com.gradle.plugin-publish") version "0.17.0"
    `kotlin-dsl`
    `maven-publish`
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
    implementation("org.ow2.asm:asm:7.1")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
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
    isAutomatedPublishing = false
}

pluginBundle {
    website = "https://github.com/anatawa12/compile-time-constant"
    vcsUrl = "https://github.com/anatawa12/compile-time-constant"
    tags = listOf("buildconstants", "constants")
    mavenCoordinates {
        groupId = "${project.group}"
    }
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
