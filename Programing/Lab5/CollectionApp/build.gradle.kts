
plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.fasterxml.jackson.core:jackson-core:2.15.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.15.0")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")


}

tasks.test {
    useJUnitPlatform()
}

val jar by tasks.getting(Jar::class) {
    manifest {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        attributes["Main-Class"] = "org.example.Main"
    }

    from(configurations
        .runtimeClasspath
        .get()
        // .get() // uncomment this on Gradle 6+
        // .files
        .map { if (it.isDirectory) it else zipTree(it) })
}