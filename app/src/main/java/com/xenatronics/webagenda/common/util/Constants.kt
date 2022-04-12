package com.xenatronics.webagenda.common.util

import androidx.compose.ui.unit.dp

object Constants {
    const val DELAY_SPLASH = 0
    private const val URL_BASE = "192.168.1.68"

    private const val URL_RDV = "http://$URL_BASE/api/v22/agenda/rdv"
    const val GET_RDV = "$URL_RDV/get"
    const val GET_ALL_RDV = "$URL_RDV/all"
    const val CLEAR_RDV = "$URL_RDV/clear"
    const val ADD_RDV = "$URL_RDV/add"
    const val UPDATE_RDV = "$URL_RDV/update"
    const val DEL_RDV = "$URL_RDV/delete"

    private const val URL_CONTACT = "http://$URL_BASE/api/v22/agenda/contact"
    const val GET_ALL_CONTACT = "$URL_CONTACT/all"
    const val CLEAR_CONTACT = "$URL_CONTACT/clear"
    const val ADD_CONTACT = "$URL_CONTACT/add"
    const val UPDATE_CONTACT = "$URL_CONTACT/update"
    const val DELETE_CONTACT = "$URL_CONTACT/delete"
    const val GET_CONTACT = "$URL_CONTACT/get"

    private const val URL_USER = "http://$URL_BASE/api/v22/agenda"
    const val LOGIN = "$URL_USER/login"
    const val LOGOUT = "$URL_USER/logout"
    const val REGISTER = "$URL_USER/register"
    const val GET_ALL_USER = "$URL_USER/getAll"


    const val EXPAND_ANIMATION_DURATION = 240
    const val FADE_OUT_ANIMATION_DURATION = 100
    const val FADE_IN_ANIMATION_DURATION = 200
    val HEIGHT_COMPONENT = 57.dp
    const val RADIUS_SMALL = 40
    const val RADIUS_MEDIUM = 50
    val TOP_SPACE = 10.dp
    const val SHRINK_DELAY = 300
    //const val SWIPE_REFRESH_DELAY=1200L
}