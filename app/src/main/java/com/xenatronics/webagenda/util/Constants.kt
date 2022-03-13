package com.xenatronics.webagenda.util

import androidx.compose.ui.unit.dp

object Constants {
    const val DELAY_SPLASH=0
    const val IP="192.168.1.68"

    const val URL_RDV="http://$IP/api/V22/agenda/tasks"
    const val GET_ALL_RDV="$URL_RDV/all"
    const val CLEAR_RDV="$URL_RDV/clear"
    const val ADD_RDV="$URL_RDV/add"
    const val UPDATE_RDV="$URL_RDV/update"
    const val DEL_RDV="$URL_RDV/delete"

    const val URL_CONTACT="http://$IP/api/V22/agenda/contact"
    const val GET_ALL_CONTACT="$URL_CONTACT/all"
    const val CLEAR_CONTACT="$URL_CONTACT/clear"
    const val ADD_CONTACT="$URL_CONTACT/add"
    const val UPDATE_CONTACT="$URL_CONTACT/update"
    const val DELETE_CONTACT="$URL_CONTACT/delete"
    const val GET_CONTACT="$URL_CONTACT/get"


    const val EXPAND_ANIMATION_DURATION = 450
    const val FADE_OUT_ANIMATION_DURATION = 100
    const val FADE_IN_ANIMATION_DURATION = 200
    val HEIGHT_COMPONENT=57.dp
    const val RADIUS_SMALL=40
    const val RADIUS_MEDIUM=50
}