package ru.playzone

import io.ktor.server.application.Application
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import ru.playzone.features.games.configureGameRouting
import ru.playzone.features.login.configureLoginRouting
import ru.playzone.features.register.configureRegisterRouting
import ru.playzone.plugins.*

fun main() {
//    val config = HikariConfig("hikari.properties")
//    val dataSource = HikariDataSource(config)
    Database.connect(
        url = "jdbc:postgresql://containers-us-west-100.railway.app:5597/railway",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "Jcj4YRHe85XolHE1JSp3"
    )

    embeddedServer(Netty, port = System.getenv("PORT").toInt(), module = Application::applicationModule).start(wait = true)
}

fun Application.applicationModule() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureGameRouting()
    configureSerialization()
}
