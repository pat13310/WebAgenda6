package com.xenatronics.webagenda.presentation.screens.new_contact

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class NewContactState(
    val id: Int = 0,
    var nom: String = "",
    var adresse: String = "",
    var cp: String = "",
    var ville: String = "",
    var tel: String = "",
    var email: String = ""
)
