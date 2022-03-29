package com.xenatronics.webagenda.util

import java.util.*

fun detectMonth(month: String): Int {
    return when (month) {
        "janvier" -> 0
        "février" -> 1
        "mars" -> 2
        "avril" -> 3
        "mai" -> 4
        "juin" -> 5
        "juillet" -> 6
        "août"->7
        "septembre"->8
        "octobre"->9
        "novembre"->10
        "décembre"->11
        else ->  0
    }
}

fun calendarSetTime(time:String,calendar:Calendar){
    val times=time.split(":")
    calendar.set(Calendar.HOUR_OF_DAY, 24)
    calendar.set(Calendar.HOUR_OF_DAY,times[0].toInt())
    calendar.set(Calendar.MINUTE,times[1].toInt())
}

fun calendarSetDate(date:String,calendar:Calendar){
    val dates=date.split(" ")
    calendar.set(dates[2].toInt(),detectMonth(dates[1]),dates[0].toInt())
}