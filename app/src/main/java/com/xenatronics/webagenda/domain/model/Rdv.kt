package com.xenatronics.webagenda.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Rdv(
    val id: Int=0,
    var nom: String="",
    var date: Long=0,
    var id_contact: Int = -1
)




