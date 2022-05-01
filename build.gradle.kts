plugins {
    id("com.github.ben-manes.versions") version "0.39.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
    id("com.vanniktech.maven.publish") version "0.19.0"
    id("org.jetbrains.dokka") version "1.4.32"
    kotlin("jvm") version "1.6.0"
    `maven-publish`
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "org.sangeet"
    version = version

}

publishing {
    repositories {
        maven {
            val releasesRepoUrl = layout.buildDirectory.dir("$buildDir/repos/releases")
            val snapshotsRepoUrl = layout.buildDirectory.dir("$buildDir/repos/snapshots")
            url =
              if ((version as String).endsWith("SNAPSHOT")) uri(snapshotsRepoUrl)
              else uri(releasesRepoUrl)
        }
    }
}

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
    }
}
