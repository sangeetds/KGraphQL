plugins {
    id("com.github.ben-manes.versions") version "0.39.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
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
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.sangeet"
            artifactId = "kgraphql"
            version = project.version as String


        }
    }
}

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
    }
}
