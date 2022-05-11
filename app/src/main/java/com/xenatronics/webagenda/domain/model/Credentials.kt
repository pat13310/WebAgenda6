package com.xenatronics.webagenda.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Credentials (val name:String="",
                        val password:String="",
                        val mail:String="")
