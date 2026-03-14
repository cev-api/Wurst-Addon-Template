import java.io.File

plugins {
    alias(libs.plugins.fabric.loom)
}

base {
    archivesName = properties["archives_base_name"] as String
    version = libs.versions.mod.version.get()
    group = properties["maven_group"] as String
}

repositories {
    mavenCentral()
}

val wurstDevJar = resolveWurstDevJar()
val wurstRuntimeJar = resolveWurstRuntimeJar(wurstDevJar)

dependencies {
    // Minecraft + Fabric
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)

    // Wurst API / dev jar (named mappings)
    compileOnly(files(wurstDevJar))

    // Optional runtime jar so IDE run configs can launch with Wurst loaded.
    if (wurstRuntimeJar != null) {
        modLocalRuntime(files(wurstRuntimeJar))
    }
}

fun resolveWurstDevJar(): File {
    val configuredPath = providers.gradleProperty("wurst_dev_jar").orNull
    if (configuredPath != null) {
        val configured = file(configuredPath)
        if (configured.exists()) return configured
    }

    val autoSearchDirs = listOf(
        file("../build/devlibs"),
        file("../Wurst7-master/build/devlibs"),
        file("../../Wurst7-master/build/devlibs")
    )
    for (dir in autoSearchDirs) {
        if (!dir.isDirectory) continue

        val candidate = dir.listFiles()
            ?.filter { it.isFile && it.name.endsWith("-dev.jar") }
            ?.maxByOrNull { it.lastModified() }
        if (candidate != null) return candidate
    }

    throw GradleException(
        "Could not find a Wurst dev jar. Set -Pwurst_dev_jar=<path> " +
            "or place one at libs/wurst-dev.jar."
    )
}

fun resolveWurstRuntimeJar(wurstDevJar: File): File? {
    val configuredPath = providers.gradleProperty("wurst_runtime_jar").orNull
    if (configuredPath != null) {
        val configured = file(configuredPath)
        if (configured.exists()) return configured
    }

    val defaultRuntime = file("libs/wurst.jar")
    if (defaultRuntime.exists()) return defaultRuntime

    if (wurstDevJar.name.endsWith("-dev.jar")) {
        val candidateName = wurstDevJar.name.removeSuffix("-dev.jar") + ".jar"
        val libsDir = File(wurstDevJar.parentFile?.parentFile, "libs")
        val derived = File(libsDir, candidateName)
        if (derived.exists()) return derived
    }

    val autoSearchDirs = listOf(
        file("../build/libs"),
        file("../Wurst7-master/build/libs"),
        file("../../Wurst7-master/build/libs")
    )
    for (dir in autoSearchDirs) {
        if (!dir.isDirectory) continue

        val candidate = dir.listFiles()
            ?.filter { it.isFile && it.name.endsWith(".jar") }
            ?.filterNot { it.name.endsWith("-sources.jar") }
            ?.maxByOrNull { it.lastModified() }
        if (candidate != null) return candidate
    }

    return null
}

tasks {
    processResources {
        val propertyMap = mapOf(
            "version" to project.version,
            "mc_version" to libs.versions.minecraft.get()
        )

        inputs.properties(propertyMap)

        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(propertyMap)
        }
    }

    jar {
        inputs.property("archivesName", project.base.archivesName.get())

        from("LICENSE") {
            rename { "${it}_${inputs.properties["archivesName"]}" }
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 21
        options.compilerArgs.add("-Xlint:deprecation")
        options.compilerArgs.add("-Xlint:unchecked")
    }
}
