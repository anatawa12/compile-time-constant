package com.anatawa12.compileTimeConstant

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention

/**
 * Created by anatawa12 on 2018/11/14.
 */
fun Project.apply() {
	plugins.apply(JavaPlugin::class.java)

	val extension = project.extensions.create("compileTimeConstant", CompileTimeConstantExtension::class.java)

	val sourceSets = project.convention.findPlugin(JavaPluginConvention::class.java)?.sourceSets ?: return;

	sourceSets.all { sourceSet ->
		val createConstantsTask =
			tasks.create(sourceSet.getTaskName("create", " compile time constant"), CreateConstantsTask::class.java)
				.apply {
					sourceSetName = sourceSet.name
				}
		afterEvaluate {
			dependencies.add(sourceSet.getTaskName("", "implementation"), files(createConstantsTask.output)
				.builtBy(createConstantsTask))
		}
	}
}
