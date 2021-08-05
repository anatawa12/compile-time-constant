settingsEvaluated {
    pluginManagement.repositories {
        mavenLocal()
        gradlePluginPortal()
    }
    pluginManagement.resolutionStrategy.eachPlugin {
        if (requested.id.id == "com.anatawa12.compile-time-constant") {
            useModule("com.anatawa12:compile-time-constant:"
                    + providers.gradleProperty("compile-time-constant-version")
                .forUseAtConfigurationTime().get())
        }
    }
}
