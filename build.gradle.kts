import org.gradle.plugins.ide.idea.model.IdeaModel
import org.gradle.plugins.ide.idea.model.IdeaModule
import org.jetbrains.gradle.ext.ModuleSettings
import org.jetbrains.gradle.ext.PackagePrefixContainer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.21"
    id("org.jetbrains.gradle.plugin.idea-ext") version "0.5"
}

group = "sample"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

fun Project.idea(configure: IdeaModel.() -> Unit): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("idea", configure)

fun IdeaModule.settings(block: ModuleSettings.() -> Unit) =
    (this@settings as ExtensionAware).extensions.configure(block)

val ModuleSettings.packagePrefix: PackagePrefixContainer
    get() = (this@packagePrefix as ExtensionAware).extensions["packagePrefix"] as PackagePrefixContainer

idea {
    module {
        settings {
            packagePrefix["main/src"] = "org.example"
            packagePrefix["test/src"] = "org.example"
        }
    }
}

sourceSets {
    getByName("main").also {
        it.java.setSrcDirs(mutableListOf("main/src"))
        it.resources.setSrcDirs(mutableListOf("main/resources"))
    }
    getByName("test").also {
        it.java.setSrcDirs(mutableListOf("test/src"))
        it.resources.setSrcDirs(mutableListOf("test/resources"))
    }
}

kotlin {
    sourceSets {
        getByName("main").also {
            it.kotlin.setSrcDirs(mutableListOf("main/src"))
            it.resources.setSrcDirs(mutableListOf("main/resources"))
        }
        getByName("test").also {
            it.kotlin.setSrcDirs(mutableListOf("test/src"))
            it.resources.setSrcDirs(mutableListOf("test/resources"))
        }
    }
}