plugins {
    java
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // o 22 se vuoi usare la tua versione
    }
}

application {
    // la classe con il main
    mainClass.set("parcofaunistico.App")
}

dependencies {
    // esempio: driver mysql
    // implementation("mysql:mysql-connector-java:8.1.0")
    implementation("com.mysql:mysql-connector-j:8.3.0")
}
