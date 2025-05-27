package com.sap.gyo.entities

import kotlinx.serialization.Serializable


@Serializable
data class ConcurUserEntity(
    val userId: String,         // assuming Concur gives this as a UUID string
    val name: String,
    val lastName: String,
    val empID: String,
    val approver: String
)