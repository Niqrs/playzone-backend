package ru.playzone

import io.ktor.server.application.Application
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.playzone.features.games.configureGameRouting
import ru.playzone.features.login.configureLoginRouting
import ru.playzone.features.register.configureRegisterRouting
import ru.playzone.plugins.*

fun main() { //WHY?
    val config = HikariConfig("hikari.properties").apply {
        addDataSourceProperty("user", System.getenv("PGUSER"))
        addDataSourceProperty("password", System.getenv("PGPASSWORD"))
        addDataSourceProperty("portNumber", System.getenv("PGPORT"))
        addDataSourceProperty("databaseName", System.getenv("PGDATABASE"))
        addDataSourceProperty("serverName", System.getenv("PGHOST"))
    }
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), module = Application::applicationModule).start(wait = true)
}

fun Application.applicationModule() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureGameRouting()
    configureSerialization()
}
