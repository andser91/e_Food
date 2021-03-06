import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.jpa") version "1.2.71"
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
    id("org.flywaydb.flyway") version "6.0.0"
}

version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "Greenwich.SR2"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.session:spring-session-core")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation(project(":Iam-api"))


    //mysql
    implementation ("mysql:mysql-connector-java")

    //Micrometer
    implementation ("io.micrometer:micrometer-core")
    implementation ("io.micrometer:micrometer-registry-prometheus")

    //Distributed Tracing (sleuth + Zipkin + opentrace compatibility
    implementation ("org.springframework.cloud:spring-cloud-sleuth-zipkin:2.1.1.RELEASE")
    implementation ("io.opentracing.brave:brave-opentracing:0.33.11")

    //Micrometer
    implementation ("io.micrometer:micrometer-core")
    implementation ("io.micrometer:micrometer-registry-prometheus")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
