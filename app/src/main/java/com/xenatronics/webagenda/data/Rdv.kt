package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Rdv(val name: String, val date: Int, val id: Int, val id_contact: Int = -1)

@Keep
@Serializable
data class PostRdv(val name: String, val date: Long, val id_contact: Int = -1)


@Keep
@Serializable
data class ResponseSimpleRdv(
    val status:String
)

@Keep
@Serializable
data class ResponseRDV(
    val name: String = "",
    val date: Long = 0,
    val id_contact: Int = 0,
    val id: Int = 0
)
