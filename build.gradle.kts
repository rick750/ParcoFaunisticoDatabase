plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "it.parcofaunistico"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("parcofaunistico.App")
}

dependencies {
    implementation("com.mysql:mysql-connector-j:8.3.0")
    // aggiungi altre dipendenze qui
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Main-Class" to application.mainClass.get()
        )
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set(project.name)
    archiveClassifier.set("")           // rimuove il classifier
    archiveVersion.set(project.version.toString())
    manifest {
        attributes("Main-Class" to application.mainClass.get())
    }
    mergeServiceFiles() // utile per service providers
}

tasks.register("fatJar") {
    dependsOn(tasks.named("shadowJar"))
    group = "build"
    description = "Genera un JAR eseguibile contenente tutte le dipendenze (uber-jar)."
}
