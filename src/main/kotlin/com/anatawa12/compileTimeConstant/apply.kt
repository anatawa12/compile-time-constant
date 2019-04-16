package com.anatawa12.compileTimeConstant

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar
import java.io.File

/**
 * Created by anatawa12 on 2018/11/14.
 */
fun Project.apply() {
	plugins.apply(JavaPlugin::class.java)

	val sourceSets = project.convention.findPlugin(JavaPluginConvention::class.java)?.sourceSets ?: return;

	sourceSets.all { sourceSet ->
		val compileJava = tasks.getByName(sourceSet.getCompileTaskName("java"))
		val createConstantsTask =
			tasks.create(sourceSet.getTaskName("create", " compile time constant"), CreateConstantsTask::class.java)
				.apply {
					sourceSetName = sourceSet.name
				}
		afterEvaluate {
			dependencies.add(sourceSet.getTaskName("", "implementation"), files(createConstantsTask.output))
			compileJava.dependsOn.asSequence()
				.filterIsInstance<Task>()
				.filter { it.name.startsWith("compile") }
				.forEach {
					it.dependsOn(createConstantsTask)
				}
			compileJava.dependsOn(createConstantsTask)
		}
	}
}
