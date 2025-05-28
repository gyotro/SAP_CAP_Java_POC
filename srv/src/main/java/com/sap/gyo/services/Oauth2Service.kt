package com.sap.gyo.services

import io.ktor.client.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
open class OAuthTokenService(
    private val httpClient: HttpClient,
    @Value("\${concur.oauth.token-url}") private val tokenUrl: String,
    @Value("\${concur.oauth.client-id}") private val clientId: String,
    @Value("\${concur.oauth.client-secret}") private val clientSecret: String,
    @Value("\${concur.oauth.init_refresh_token}") private val refreshToken: String
) {
    private val logger = LoggerFactory.getLogger(OAuthTokenService::class.java)
    init {
        logger.info("Initializing OAuth Token Service...\n" +
                "tokenUrl: $tokenUrl\n" +
                "clientId: $clientId\n" +
                "clientSecret: $clientSecret\n" +
                "refreshToken: $refreshToken")
    }

    @Serializable
    data class TokenResponse(
        @SerialName("access_token") val accessToken: String,
        @SerialName("refresh_token") val refreshToken: String? = null,
        @SerialName("expires_in") val expiresIn: Int,
        @SerialName("token_type") val tokenType: String
    )

    suspend fun getRefreshToken(): String {
        val response: HttpResponse = httpClient.submitForm(
            url = tokenUrl,
            formParameters = Parameters.build {
                append("grant_type", "refresh_token")
                append("client_id", clientId)
                append("client_secret", clientSecret)
                append("refresh_token", refreshToken)
            }
        )
        val jsonBody = response.bodyAsText()
        return Json.decodeFromString<TokenResponse>(jsonBody).refreshToken
            ?: throw IllegalStateException("No refresh token returned")
    }

    suspend fun getAccessToken(): String {
        val refreshToken = getRefreshToken()
        val response: HttpResponse = httpClient.submitForm(
            url = tokenUrl,
            formParameters = Parameters.build {
                append("grant_type", "refresh_token")
                append("client_id", clientId)
                append("client_secret", clientSecret)
                append("refresh_token", refreshToken)
            }
        )
        val jsonBody = response.bodyAsText()
        return Json.decodeFromString<TokenResponse>(jsonBody).accessToken
    }
}
