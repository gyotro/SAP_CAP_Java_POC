package com.sap.gyo.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScimUserResponse(
    val schemas: List<String>,
    val totalResults: Int,
    val startIndex: Int,
    val itemsPerPage: Int,
    val Resources: List<ConcurUserEntity>
)

@Serializable
data class ConcurUserEntity(
    val id: String,
    val userName: String,
    val displayName: String? = null,
    val active: Boolean,
    val emails: List<Email>? = null,
    val name: Name? = null,
    val meta: Meta? = null,
    @SerialName("urn:ietf:params:scim:schemas:extension:enterprise:2.0:User")
    val enterpriseExtension: EnterpriseExtension? = null
)

@Serializable
data class Email(
    val value: String,
    val type: String,
    val verified: Boolean,
    val notifications: Boolean
)

@Serializable
data class Name(
    val givenName: String? = null,
    val familyName: String? = null,
    val formatted: String? = null
)

@Serializable
data class Meta(
    val created: String,
    val lastModified: String,
    val version: Int,
    val location: String
)

@Serializable
data class EnterpriseExtension(
    val employeeNumber: String? = null,
    val companyId: String? = null,
    val startDate: String? = null,
    val terminationDate: String? = null
)
