plugins {
    kotlin("jvm") version "1.3.72"
}

group = "org.example"
version = "1.0-SNAPSHOT"
val vertxVersion = "3.9.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-pg-client:$vertxVersion")


    implementation("io.vertx:vertx-sql-client:$vertxVersion")
    // comment out the above line and put your own path below for local jar, then ctrl-shift-o to refresh gradle projects
//    implementation(files("/home/alan/dev/3rdParty/vertx-sql-client/vertx-sql-client/target/vertx-sql-client-3.9.0.jar"))
}


// compile bytecode to java 8 (default is java 6)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}