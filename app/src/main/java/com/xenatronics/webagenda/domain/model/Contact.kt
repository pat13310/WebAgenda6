package com.xenatronics.webagenda.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Contact(
    val id: Int = 0,
    var nom: String = "",
    var adresse: String = "",
    var cp: String = "",
    var ville: String = "",
    var tel: String = "",
    var mail: String = "",
    var selected: Boolean = false
)

