package com.xenatronics.webagenda.util

import androidx.compose.ui.unit.dp

object Constants {
    const val IP="192.168.1.68"
    const val URL="http://$IP/api/V21/agenda/tasks"
    const val GET_TASKS="$URL/all"
    const val CLEAR_TASKS="$URL/clear"
    const val ADD_TASK="$URL/add"
    const val DEL_TASK="$URL/delete"
    const val EXPAND_ANIMATION_DURATION = 450
    const val FADE_OUT_ANIMATION_DURATION = 100
    const val FADE_IN_ANIMATION_DURATION = 200
    val HEIGHT_COMPONENT=57.dp
}