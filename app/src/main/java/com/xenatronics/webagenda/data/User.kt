package com.xenatronics.webagenda.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class User (val name:String="",
                 val password:String="",
                 val mail:String="")
