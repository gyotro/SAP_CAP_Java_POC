package com.sap.gyo.httpHandlers

import com.sap.gyo.Application
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class KtorClientConfig {

    private val logger = LoggerFactory.getLogger(Application::class.java)
    @Bean
    open fun ktorHttpClient(): HttpClient {
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
        logger.info("Initializing Ktor Client...")
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(json) // 🟢 register Json instance correctly here
            }
            expectSuccess = true
        }
    }
}
