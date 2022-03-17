package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class Contact(
    val id: Int,
    val nom: String,
    val adresse: String,
    val cp: String,
    val ville: String,
    val tel: String,
    val mail: String,
)

@Keep
@Serializable
data class PostContact(
    val nom: String,
    val adresse: String = "",
    val cp: String = "",
    val ville: String = "",
    val tel: String = "",
    val mail: String = "",
)

@Keep
@Serializable
data class ResponseSimpleContact(
    val Status: String
)

@Keep
@Serializable
data class ResponseContact(
    val id: Int = 0,
    var nom: String = "",
    var adresse: String = "",
    var cp: String = "",
    var ville: String = "",
    var tel: String = "",
    var mail: String = "",
    var status: String = "",
)

@Keep
@Serializable
data class PostID(
    val id: Int
)
