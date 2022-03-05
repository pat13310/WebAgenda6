package com.xenatronics.webagenda.util

object Constants {
    const val IP="192.168.1.68"
    const val URL="http://$IP/api/V21/agenda/tasks"
    const val GET_TASKS="$URL/all"
    const val CLEAR_TASKS="$URL/clear"
    const val ADD_TASK="$URL/add"
    const val DEL_TASK="$URL/delete"

}