package com.sap.gyo.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConcurUserEntity(
    val id: String,
    val userName: String,
    val displayName: String,
    val active: Boolean,
    val timezone: String,
    val title: String? = null,
    val nickName: String? = null,
    val dateOfBirth: String? = null,
    val preferredLanguage: String? = null,
    val emails: List<Email> = emptyList(),
    val phoneNumbers: List<PhoneNumber> = emptyList(),
    val emergencyContacts: List<EmergencyContact> = emptyList(),
    val addresses: List<Address> = emptyList(),
    val schemas: List<String> = emptyList(),
    val name: Name,
    val meta: Meta,
    val localeOverrides: LocaleOverrides,

    @SerialName("urn:ietf:params:scim:schemas:extension:enterprise:2.0:User")
    val enterpriseExtension: EnterpriseExtension
)

@Serializable
data class LocaleOverrides(
    val preferenceEndDayViewHour: Int,
    val preferenceFirstDayOfWeek: String,
    val preferenceDateFormat: String,
    val preferenceCurrencySymbolLocation: String,
    val preferenceHourMinuteSeparator: String,
    val preferenceDistance: String,
    val preferenceDefaultCalView: String,
    val preference24Hour: String,
    val preferenceNumberFormat: String,
    val preferenceStartDayViewHour: Int,
    val preferenceNegativeCurrencyFormat: String,
    val preferenceNegativeNumberFormat: String
)

@Serializable
data class Address(
    val country: String? = null,
    val streetAddress: String? = null,
    val postalCode: String? = null,
    val locality: String? = null,
    val type: String,
    val region: String? = null
)

@Serializable
data class Name(
    val honorificSuffix: String? = null,
    val givenName: String,
    val familyName: String,
    val familyNamePrefix: String? = null,
    val honorificPrefix: String? = null,
    val middleName: String? = null,
    val formatted: String? = null
)

@Serializable
data class PhoneNumber(
    val issuingCountry: String? = null,
    val display: String,
    val type: String,
    val value: String,
    val notifications: Boolean? = null,
    val primary: Boolean? = null
)

@Serializable
data class Email(
    val verified: Boolean,
    val type: String,
    val value: String,
    val notifications: Boolean
)

@Serializable
data class EmergencyContact(
    val country: String? = null,
    val streetAddress: String? = null,
    val postalCode: String? = null,
    val name: String,
    val locality: String? = null,
    val phones: List<String> = emptyList(),
    val region: String? = null,
    val relationship: String
)

@Serializable
data class Meta(
    val resourceType: String,
    val created: String,
    val lastModified: String,
    val version: Int,
    val location: String
)

@Serializable
data class EnterpriseExtension(
    val terminationDate: String? = null,
    val companyId: String,
    val costCenter: String? = null,
    val startDate: String,
    val employeeNumber: String
)
