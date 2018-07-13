buildscript {
    ext.kotlin_version = '1.2.20'
    repositories {
        mavenCentral()
        maven { url 'http://dl.bintray.com/jetbrains/intellij-plugin-service'}
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

plugins {
    id "org.jetbrains.intellij" version "0.3.1"
}

repositories {
    mavenCentral()
}


tasks.withType(JavaCompile) {
    options.encoding = "UTF-8"
}

apply plugin: 'idea'
apply plugin: 'org.jetbrains.intellij'
apply plugin: 'java'
apply plugin: 'kotlin'

intellij {
//    IC-2016.1  IC-14.1.4 IC-2016.3  IU-2016.1  IC-2017.3  IC-2017.2
//    IC-2016.1  IC-14.1.4 IC-2016.3  IU-2016.1  IU-2018.1  IU-2016.3
    version 'IU-2018.1' //IntelliJ IDEA dependency
//    plugins 'coverage' //Bundled plugin dependencies
    pluginName 'MyBatisCodeHelper-Pro'
    updateSinceUntilBuild false
//    需要来填写publish这块 类似
//    publish {
//        username 'AmailP'
//        pluginId '7415'
//    }
    plugins 'spring'
}


dependencies{
    compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.5'
    compile group: 'commons-io', name: 'commons-io', version: '2.5'
    compile group: 'org.freemarker', name: 'freemarker', version: '2.3.23'
    compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.11'
    compile group: 'uk.com.robust-it', name: 'cloning', version: '1.9.3'
    compile 'com.squareup.okhttp3:okhttp:3.6.0'
    compileOnly group: 'org.projectlombok', name: 'lombok', version: '1.16.18'
    compile fileTree(dir: 'libs', includes: ['*.jar'])
    testCompile "org.assertj:assertj-core:2.2.0"
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testCompile group: 'org.mockito', name: 'mockito-core', version: '2.8.47'
    testCompile 'org.powermock:powermock-api-mockito2:1.7.3'
    testCompile 'org.powermock:powermock-module-junit4:1.7.3'
    testCompile group: 'org.objenesis', name: 'objenesis', version: '2.5'
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

group 'org.jetbrains'
version '1.8.5' // Plugin version