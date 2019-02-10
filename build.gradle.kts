import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.ide.idea.model.IdeaProject
import org.gradle.plugins.ide.idea.model.*
import org.jetbrains.gradle.ext.*


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


fun org.gradle.api.Project.idea(configure: org.gradle.plugins.ide.idea.model.IdeaModel.() -> Unit): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("idea", configure)

fun IdeaModule.settings(block: ModuleSettings.() -> Unit) =
    (this@settings as ExtensionAware).extensions.configure(block)

val ModuleSettings.packagePrefix: PackagePrefixContainer
    get() = (this@packagePrefix as ExtensionAware).extensions["packagePrefix"] as PackagePrefixContainer

idea {
    module {
        settings {
            packagePrefix["src"] = "org.example"
            packagePrefix["src/main/java"] = "org.example"
            packagePrefix["src/main/kotlin"] = "org.example"
            packagePrefix["../other-root/src/main/java"] = "org.example"
        }
    }
}