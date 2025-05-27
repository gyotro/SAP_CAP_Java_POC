package com.sap.gyo.entities

import kotlinx.serialization.Serializable
@Serializable
data class ConcurUser(
    val id: String,
    val name: String)
