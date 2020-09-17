plugins {
    java
    kotlin("jvm") version "1.3.61"
}

group = "dsm.service"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "dsm.service.announcement.AnnouncementApplicationKt"
    }

    from(configurations.runtime.get().map {if (it.isDirectory) it else zipTree(it)})
}
