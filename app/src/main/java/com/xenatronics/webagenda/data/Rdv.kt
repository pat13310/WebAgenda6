package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import androidx.compose.runtime.MutableState
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Rdv(val name: String, val date: Int, val id: Int, val id_contact: Int=-1)

@Keep
@Serializable
data class PostRequest(val name: String, val date: Long, val id_contact: Int=-1)


@Keep
@Serializable
data class ResponseRDV(val name: String="", val date: Long=0, val id_contact: Int=0, val id: Int=0)
