package com.anatawa12.compileTimeConstant

import jdk.internal.org.objectweb.asm.Opcodes.*
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.ClassWriter.COMPUTE_FRAMES
import org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.objectweb.asm.Opcodes.V1_6
import org.objectweb.asm.Type.*
import java.io.File
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.util.concurrent.Callable
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@CacheableTask
open class CreateConstantsTask() : DefaultTask() {
    private val alwaysGenerateJarFile get() = project.extensions
        .findByType(CompileTimeConstantExtension::class.java)
        ?.alwaysGenerateJarFile
        ?: false

    init {
        inputs.property("alwaysGenerateJarFile", Callable { alwaysGenerateJarFile })
    }

    @get:Input
    var sourceSetName: String? = null
        set(value) {
            field = value
            output = File(project.buildDir, "compile-time-constant/constants-$sourceSetName.jar")
        }

    @OutputFile
    var output: File = File(project.buildDir, "compile-time-constant/constants-$sourceSetName.jar")
        private set

    @Input
    var constantsClass: String = ""

    @get:Input
    val values: Map<String, Serializable>
        get() = _values.toMap()

    private val _values: MutableMap<String, Serializable> = mutableMapOf()

    fun values(pairs: Map<String, Serializable>) {
        for ((_, value) in pairs) {
            when (value) {
                is Int -> {}
                is Byte -> {}
                is Char -> {}
                is Short -> {}
                is Boolean -> {}
                is Float -> {}
                is Long -> {}
                is Double -> {}
                is String -> {}
                else -> throw IllegalArgumentException("value expected primitive type or String. but ${value.javaClass.simpleName}")
            }
        }
        _values.putAll(pairs)
    }

    @TaskAction
    fun createConstants() {
        val constantsClass = constantsClass.replace('.', '/')
        if (constantsClass == "") {
            System.err.println("No value has been specified for property 'constantsClass'.")
            if (alwaysGenerateJarFile) generateEmptyJarFile()
            return
        }
        val cw = ClassWriter(COMPUTE_FRAMES)
        val cv = cw as ClassVisitor
        cv.visit(V1_6, ACC_PUBLIC + ACC_FINAL + ACC_SUPER, constantsClass, null, "java/lang/Object", null)
        for ((name, value) in values) {
            val descriptor = when (value) {
                is Int -> INT_TYPE.descriptor
                is Byte -> BYTE_TYPE.descriptor
                is Char -> CHAR_TYPE.descriptor
                is Short -> SHORT_TYPE.descriptor
                is Boolean -> BOOLEAN_TYPE.descriptor
                is Float -> FLOAT_TYPE.descriptor
                is Long -> LONG_TYPE.descriptor
                is Double -> DOUBLE_TYPE.descriptor
                is String -> "L${"java/lang/String"};"
                else -> throw IllegalArgumentException("value must be primitive type or String")
            }
            val fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, name, descriptor, null, value)
            fv.visitAnnotation("L${"javax/annotation/Nonnull"};", true).visitEnd()
            fv.visitEnd()
        }
        val mv = cv.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null)
        mv.visitCode()
        mv.visitVarInsn(ALOAD, 0)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
        mv.visitMaxs(1, 1)
        mv.visitEnd()
        cv.visitEnd()
        output.parentFile.mkdirs()
        ZipOutputStream(output.outputStream()).use { zipOut ->
            zipOut.putNextEntry(ZipEntry("$constantsClass.class"))
            zipOut.write(cw.toByteArray())
            zipOut.closeEntry()
        }
    }

    private fun generateEmptyJarFile() {
        output.parentFile.mkdirs()
        ZipOutputStream(output.outputStream()).use {}
    }
}
