import java.time.Duration

val version: String by project

plugins {
    id("com.github.ben-manes.versions") version "0.39.0"
    jacoco
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "com.apurebase"
    version = version
}

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
    }
}
