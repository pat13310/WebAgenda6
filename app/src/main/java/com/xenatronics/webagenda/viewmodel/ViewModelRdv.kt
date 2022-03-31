package com.xenatronics.webagenda.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.PostID
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.repository.RepositoryContact
import com.xenatronics.webagenda.repository.RepositoryRdv
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.getDateFormatter
import com.xenatronics.webagenda.util.getTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewModelRdv @Inject constructor() : ViewModel() {
    val action = mutableStateOf(Action.NO_ACTION)
    val allRdvFlow = MutableStateFlow<List<Rdv>>(emptyList())
    val allContactFlow = MutableStateFlow<List<Contact>>(emptyList())
    private val isSateChanged = MutableStateFlow(false)
    val nom: MutableState<String> = mutableStateOf("")
    var timestamp = mutableStateOf(0L)

    var calendar = mutableStateOf(Calendar.getInstance())
    var time = mutableStateOf("")
    var date = mutableStateOf("")

    // rdv sélectionné
    var selectRdv = mutableStateOf(Rdv())

    //contact actif
    val selectContact = mutableStateOf(Contact())


    init {
        Locale.setDefault(Locale.FRANCE)
        // init time
        timestamp.value = calendar.value.timeInMillis
        date.value = getDateFormatter(timestamp.value)
        //init date
        time.value = getTimeFormatter(timestamp.value)
    }

    fun loadRdv() {
        Log.d("Rdv", "load rdv")
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.getAllRdv()
            }.onFailure {
                allRdvFlow.value = emptyList()
                isSateChanged.value = true
            }.onSuccess {
                allRdvFlow.value = it
                isSateChanged.value = true
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

    fun handleRdvAction(action: Action) {
        when (action) {
            Action.DELETE -> {
                allRdvFlow.value.toMutableList().remove(selectRdv.value)
                deleteRdv(selectRdv.value)
            }
            Action.ADD -> {
                addRdv(selectRdv.value)
            }
            Action.UPDATE -> {
                updateRdv(selectRdv.value)
            }
            Action.DELETE_ALL -> {
                cleanRdv()
                allRdvFlow.value.toMutableList().clear()
            }
            else -> {}
        }
    }

    private fun addRdv(rdv: Rdv) {
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

    private fun updateRdv(rdv: Rdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.updateRdv(rdv)
            }.onSuccess {
                isSateChanged.value = true
            }.onFailure {
                isSateChanged.value = false
            }
        }
    }

    private fun deleteRdv(rdv: Rdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.deleteRdv(PostID(rdv.id))
            }.onSuccess {
                isSateChanged.value = true
            }.onFailure {
                isSateChanged.value = false
            }
        }
    }

    private fun cleanRdv() {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.clearRdv()
            }.onSuccess {
                isSateChanged.value = true
            }.onFailure {
                isSateChanged.value = false
            }
        }
    }

    fun updateTimeStamp(calendar: Calendar) {
        timestamp.value = calendar.timeInMillis
    }

    fun updateFields() {
        selectRdv.value.nom = nom.value
        selectRdv.value.date = calendar.value.timeInMillis
        selectRdv.value.id_contact = selectContact.value.id
    }

    fun getContact(id: Int): Contact? {
        try {
            if (allContactFlow.value.isEmpty())
                return Contact()
            return allContactFlow.value.toMutableList().find {
                it.id == id
            }
        }
        catch (e: ArrayIndexOutOfBoundsException) {
            return Contact()
        }
    }
}

