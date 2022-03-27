package com.xenatronics.webagenda.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.repository.RepositoryContact
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
    val allContactFlow = MutableStateFlow<List<Contact>>(emptyList())
    private val isSateChanged = MutableStateFlow(false)
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    val nom: MutableState<String> = mutableStateOf("")
    var timestamp= mutableStateOf(0L)
    var time = mutableStateOf("")
    var date = mutableStateOf("")
    var selectRdv =  mutableStateOf(Rdv())


    init {
        Locale.setDefault(Locale.FRANCE)
        // init time
        val milliseconds = Date().time
        date.value=dateFormatter(milliseconds, "dd LLLL yyyy")
        //init date
        time.value=dateFormatter(milliseconds,"HH:mm" )
    }

    fun loadRdv() {
        Log.d("rdv", "load")
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

    fun loadContact() {
        Log.d("Rdv", "load contact")
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryContact.getAllContact()
            }.onFailure {
                allContactFlow.value = emptyList()
                //isSateChanged.value=true
            }.onSuccess {
                allContactFlow.value = it
                //isSateChanged.value=true
            }
        }
    }


    fun addRdv(rdv: Rdv) {
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

    private fun dateFormatter(milliseconds: Long?, pattern:String="dd/MM/yyyy"): String {
        milliseconds?.let {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            return formatter.format(calendar.time)
        }
        return ""
    }
}