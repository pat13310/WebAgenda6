package com.xenatronics.webagenda.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PostID(
    val id: Int
)