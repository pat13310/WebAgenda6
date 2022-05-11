package com.xenatronics.webagenda.common.util

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.domain.usecase.ResultUseCase
import java.text.SimpleDateFormat
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

fun calendarSetTime(time: String, calendar:Calendar){
    val times=time.split(":")
    calendar.set(Calendar.HOUR_OF_DAY, 24)
    calendar.set(Calendar.HOUR_OF_DAY,times[0].toInt())
    calendar.set(Calendar.MINUTE,times[1].toInt())
}

fun calendarSetDate(date:String, calendar: Calendar){
    val dates=date.split(" ")
    calendar.set(dates[2].toInt(),detectMonth(dates[1]),dates[0].toInt())
    Log.d("Rdv : calendarSetDate", calendar.timeInMillis.toString())
}


private fun dateFormatter(milliseconds: Long?, pattern: String = "dd/MM/yyyy"): String {
    milliseconds?.let {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
    return ""
}

fun getTimeFormatter(time: Long):String{
    return dateFormatter(time,"HH:mm")
}

fun getDateFormatter(date: Long):String{
    return dateFormatter(date,"dd LLLL yyyy")
}

fun getMessage(context: Context, resultUseCase: ResultUseCase): String {
    return when (resultUseCase) {
        is ResultUseCase.Empty->{
            return context.getString(R.string.empty)
        }
        is ResultUseCase.TooShort->{
            return context.getString(R.string.tooShort)
        }
        is ResultUseCase.PatternInvalid->{
            return context.getString(R.string.patternInvalid)
        }
        is ResultUseCase.NoDigits -> {
            return context.getString( R.string.noDigits)
        }
        else -> ""
    }
}