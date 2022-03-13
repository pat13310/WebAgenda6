package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class ViewModelAdd:ViewModel() {


    val textContactName:MutableState<String> = mutableStateOf("")
    val date:MutableState<String> = mutableStateOf("")
    val time:MutableState<String> = mutableStateOf("")

    init{
        val calendar = Calendar.getInstance(Locale.getDefault())
        val formatter = SimpleDateFormat("dd LLLL yyyy", Locale.FRANCE)
        val formatTime = SimpleDateFormat("HH:mm", Locale.FRANCE)
        date.value=formatter.format(calendar.time)
        time.value=formatTime.format(calendar.time)
    }
}