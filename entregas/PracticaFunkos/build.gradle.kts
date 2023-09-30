plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Añadimos las dependencias de las librerías JDBC que vayamos a usar
    // SQLite
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    // H2, solo usa una
    implementation("com.h2database:h2:2.2.224")
    // Ibatis lo usaremos para leer los scripts SQL desde archivos
    implementation("org.mybatis:mybatis:3.5.13")
    // Lombook para generar código
    implementation("org.projectlombok:lombok:1.18.26")
}

tasks.test {
    useJUnitPlatform()
}