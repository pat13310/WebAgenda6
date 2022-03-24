package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.data.PostRdv
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.repository.RepositoryRdv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewModelRdv @Inject constructor() : ViewModel() {
    val allRdvFlow = MutableStateFlow<List<Rdv>>(emptyList())
    val isSateChanged = MutableStateFlow(false)
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    val nom: MutableState<String> = mutableStateOf("")
    var timestamp= mutableStateOf(0L)
    var time = mutableStateOf("")
    var date = mutableStateOf("")


    init {

        val _timestamp = Date().time
        date.value=dateFormatter(_timestamp, "dd LLLL yyyy")
        // init time
        time.value=dateFormatter(_timestamp,"HH:mm" )
        //init date
    }

    fun load() {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.getAllRdv()
            }.onFailure {
                allRdvFlow.value = emptyList()
                isSateChanged.value=true
            }.onSuccess {
                allRdvFlow.value = it
                isSateChanged.value=true
            }
        }
    }



    fun addRdv(rdv: PostRdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.addRdv(rdv)
            }.onSuccess {
                isSateChanged.value = true
            }.onFailure {
                isSateChanged.value = false
            }
        }
    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }

    fun dateFormatter(milliseconds: Long?, pattern:String="dd/MM/yyyy"): String {
        milliseconds?.let {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            return formatter.format(calendar.time)
        }
        return ""
    }
}