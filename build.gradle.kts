plugins {
    id 'java'
    id 'application'
    id 'com.github.johnrengelman.shadow' version '8.1.1'
}

group = 'it.parcofaunistico'
version = '1.0.0'

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
}

jar {
    manifest {
        attributes(
            'Implementation-Title': project.name,
            'Implementation-Version': project.version,
            'Main-Class': application.mainClass.get()
        )
    }
}

shadowJar {
    archiveBaseName.set(project.name)
    archiveClassifier.set('') 
    archiveVersion.set(project.version.toString())
    manifest {
        attributes 'Main-Class': application.mainClass.get()
    }
    mergeServiceFiles() 
}

// Optional: alias task per chiarezza
tasks.register('fatJar') {
    dependsOn tasks.named('shadowJar')
    group = 'build'
    description = 'Genera un JAR eseguibile contenente tutte le dipendenze (uber-jar).'
}
