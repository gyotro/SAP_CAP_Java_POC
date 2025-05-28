package com.sap.gyo.services

import com.sap.gyo.entities.ConcurUserEntity
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
open class ConcurUserService(
    private val httpClient: HttpClient,
    private val tokenService: OAuthTokenService,
    @Value("\${concur.user.api-url}") private val userApiUrl: String,
) {

    suspend fun getUsers(): List<ConcurUserEntity> {
        // Step 1: Get bearer token from refresh token
        val accessToken = tokenService.getAccessToken()

        // Step 2: Perform GET request to Concur Users API
        val response: String = httpClient.get(userApiUrl) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $accessToken")
                append(HttpHeaders.Accept, "application/json")
            }
        }.body()

        // Step 3: Deserialize JSON array into list of ConcurUser
        val concurUsers: List<ConcurUserEntity> = Json {
            ignoreUnknownKeys = true
        }.decodeFromString(response)
        return concurUsers
    }
}
