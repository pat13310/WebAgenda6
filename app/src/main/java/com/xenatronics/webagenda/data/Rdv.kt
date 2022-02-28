package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import androidx.compose.runtime.MutableState
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Rdv(val name: String, val date: Int, val id: Int, val id_infos: Int=-1)

@Keep
@Serializable
data class PostRequest(val name: String, val date: Long, val id_infos: Int=-1)

@Keep
@Serializable
data class Data(
    val adresse: String,
    val cp: String,
    val ville: String,
    val mail: String,
    val tel: String,
    val id_infos: Int
)

enum class Priority {
    HIGH,
    LOW,
    MEDIUM
}

@Keep
@Serializable
data class CardResponse(val name: String="", val date: Long=0, val id_infos: Int=0, val id: Int=0)
