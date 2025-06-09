plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

application {
    mainClass.set("org.example.Main")
}

group = "org.example"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.fasterxml.jackson.core:jackson-core:2.15.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.15.0")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")


}


javafx {
    version = "20"
    modules = mutableListOf("javafx.controls", "javafx.fxml")
}


tasks.register<Jar>("fatJar") {
    archiveBaseName.set("my-application")
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}