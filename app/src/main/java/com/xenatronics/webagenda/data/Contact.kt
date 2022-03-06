package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable



@Keep
@Serializable
data class Contact(
    val adresse: String,
    val cp: String,
    val ville: String,
    val mail: String,
    val tel: String,
    val id_infos: Int
)