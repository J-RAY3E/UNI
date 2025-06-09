plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.7.3")

}

javafx {
    version = "20"
    modules = mutableListOf("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}