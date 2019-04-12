# Compile Time Constant
This is a plugin to create constants class from gradle.

## How to use
please add this to head of `build.gradle`.

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    
    dependencies {
        classpath "com.anatawa12:compile-time-constant:1.0.0"
    }
}

// if you using plugins block, please write here.

apply plugin: 'com.anatawa12.compile-time-constant'
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

## License

This Software is released under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Library Licenses

This Software using Gradle and Kotlin Standard Library under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
This Software using ASM under [3-clause BSD License](https://asm.ow2.io/license.html)
