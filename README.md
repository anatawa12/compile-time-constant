# Compile Time Constant

[![a12 maintenance: Slowly](https://api.anatawa12.com/short/a12-slowly-svg)](https://api.anatawa12.com/short/a12-slowly-doc)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/anatawa12/compile-time-constant/com.anatawa12.compile-time-constant.gradle.plugin/maven-metadata.xml.svg?colorB=007ec6&label=gradle&logo=gradle)](https://plugins.gradle.org/plugin/com.anatawa12.compile-time-constant)
[![Discord](https://img.shields.io/discord/834256470580396043)](https://discord.gg/yzEdnuJMXv)

This is a plugin to create constants class from gradle.

## How to use
please add this to head of `build.gradle`.

```groovy
plugins {
  id("com.anatawa12.compile-time-constant") version "1.0.5"
}
```

And please set options in task. 
Task name for sourceSet `main` is `createCompileTimeConstant` and for other sourceSet is `create<sourceSet name>CompileTimeConstant`

## Options

- output\
  This type is `File`.
  this is file to jar including constants class.\
  default is `"{$project.buildDir}/compile-time-constant/constants-$sourceSetName.jar"`
- constantsClass\
  This type is `String`.
  this is name of constants class.
- values(Map<String, Object>)
  This is method.\
  Map value must be either wrapped primitive or `String`.
  
## Global Options

can be configured with `compileTimeConstant {}`

- ``alwaysGenerateJarFile``

  If this is true, if the `constantsClass` is not set, this plugin will generate empty jar file at output path.
  If false, this plugin will never generate jar file.

  Defaults false.

## License

This Software is released under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Library Licenses

This Software using Gradle and Kotlin Standard Library under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
This Software using ASM under [3-clause BSD License](https://asm.ow2.io/license.html)
