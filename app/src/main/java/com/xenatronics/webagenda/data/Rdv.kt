package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Rdv(
    val id: Int=0,
    val nom: String="",
    val date: Int=0,
    val id_contact: Int = -1
)


@Keep
@Serializable
data class ResponseSimpleRdv(
    val status:String
)


