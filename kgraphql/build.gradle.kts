import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm

plugins {
    id("com.vanniktech.maven.publish.base")
    base
    kotlin("jvm")
    id("org.jetbrains.dokka")
    signing
}

val caffeine_version: String by project
val kDataLoader_version: String by project
val deferredJsonBuilder_version: String by project
val kotlin_version: String by project
val serialization_version: String by project
val coroutine_version: String by project
val jackson_version: String by project

val netty_version: String by project
val hamcrest_version: String by project
val kluent_version: String by project
val junit_version: String by project

val isReleaseVersion = !version.toString().endsWith("SNAPSHOT")

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.vanniktech:gradle-maven-publish-plugin:0.19.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version") // JVM dependency

    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")

    implementation("com.github.ben-manes.caffeine:caffeine:$caffeine_version")
    implementation("com.apurebase:DeferredJsonBuilder:$deferredJsonBuilder_version")
    api("de.nidomiro:KDataLoader:$kDataLoader_version")


    testImplementation("io.netty:netty-all:$netty_version")
    testImplementation("org.hamcrest:hamcrest:$hamcrest_version")
    testImplementation("org.amshove.kluent:kluent:$kluent_version")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutine_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutine_version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit_version")
}

tasks {
    compileKotlin { kotlinOptions { jvmTarget = "1.8" } }
    compileTestKotlin { kotlinOptions { jvmTarget = "1.8" } }

    test {
        useJUnitPlatform()
    }
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("javadoc"))
        dokkaSourceSets {
            configureEach {
                jdkVersion.set(8)
                reportUndocumented.set(true)
                platform.set(org.jetbrains.dokka.Platform.jvm)
            }
        }
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    classifier = "javadoc"
    from(tasks.dokkaHtml)
}

signing {
    isRequired = isReleaseVersion
    useInMemoryPgpKeys(
        System.getenv("ORG_GRADLE_PROJECT_signingKey"),
        System.getenv("ORG_GRADLE_PROJECT_signingPassword")
    )
}

mavenPublishing {
    configure(
        KotlinJvm(
            JavadocJar.Dokka("dokkaHtml"),
            true
        )
    )
}
