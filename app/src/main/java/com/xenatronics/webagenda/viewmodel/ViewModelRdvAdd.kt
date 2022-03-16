package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewModelRdvAdd @Inject constructor():ViewModel() {

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