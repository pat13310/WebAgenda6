package com.xenatronics.webagenda.data
import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class Contact(
    val id: Int,
    val adresse: String,
    val cp: String,
    val ville: String,
    val mail: String,
    val tel: String,
)

@Keep
@Serializable
data class SendContact(
    val name: String,
    val adresse: String,
    val cp: String,
    val ville: String,
    val tel: String,
    val mail: String,
)


@Keep
@Serializable
data class ResponseContact(
    val id: Int = 0,
    val name: String ,
    val adresse: String,
    val cp: String,
    val ville: String,
    val tel: String,
    val mail: String,
    val status: String
)
