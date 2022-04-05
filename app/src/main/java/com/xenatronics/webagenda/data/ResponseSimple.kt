package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ResponseSimple (
    val status:String=""
)