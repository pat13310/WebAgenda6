package com.xenatronics.webagenda.presentation.screens.listrdv

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.xenatronics.webagenda.common.events.ListRdvEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.usecase.Contact.UseCaseContact
import com.xenatronics.webagenda.domain.usecase.Rdv.UseCaseRdv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewModelRdv @Inject constructor(
    private val useCase: UseCaseRdv,
    private val useCaseContact: UseCaseContact
) : ViewModel() {
    val action = mutableStateOf(Action.NO_ACTION)
    val allRdvFlow = MutableStateFlow<List<Rdv>>(emptyList())
    val allContactFlow = MutableStateFlow<List<Contact>>(emptyList())
    private val isSateChanged = MutableStateFlow(false)
    val nom: MutableState<String> = mutableStateOf("")
    //var timestamp = mutableStateOf(0L)

    //private var calendar = mutableStateOf(Calendar.getInstance())
    var date = mutableStateOf("")

    // rdv sélectionné
    var selectRdv = mutableStateOf(Rdv())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun loadRdv() {
        Log.d("Rdv", "load rdv")
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.getAllRdv()
            }.onFailure {
                allRdvFlow.value = emptyList()
                isSateChanged.value = false
            }.onSuccess {
                allRdvFlow.value = it.sortedBy { rdv -> rdv.date }
                isSateChanged.value = true
            }
        }
    }

    fun loadContact() {
        viewModelScope.launch {
            kotlin.runCatching {
                useCaseContact.getAllContact()
            }.onFailure {
                allContactFlow.value = emptyList()
                //isSateChanged.value=true
            }.onSuccess {
                allContactFlow.value = it
                //isSateChanged.value=true
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun OnEvent(event: ListRdvEvent) {
        when (event) {
            ListRdvEvent.OnLogout->{
                sendUIEvent(UIEvent.Navigate(Screen.LoginScreen.route))
            }
            ListRdvEvent.OnNew -> {
                val rdv = Gson().toJson(Rdv())
                sendUIEvent(UIEvent.Navigate(Screen.NewRdvScreen.route + "/$rdv"))
            }
            ListRdvEvent.OnEdit -> {
                val rdv = Gson().toJson(selectRdv.value)
                sendUIEvent(UIEvent.Navigate(Screen.NewRdvScreen.route + "/$rdv"))
            }
            ListRdvEvent.OnAdd -> {
                addRdv(selectRdv.value)
            }
            ListRdvEvent.OnUpdate -> {
                updateRdv(selectRdv.value)
            }
            ListRdvEvent.OnQueryDelete->{
                sendUIEvent(UIEvent.ShowAlertDialog(
                    title="Suppression",
                    message="Voulez-vous supprimer le rendez-vous avec ${selectRdv.value.nom}?",
                    action = Action.DELETE
                ))
            }
            ListRdvEvent.OnDelete -> {
                allRdvFlow.value.toMutableList().remove(selectRdv.value)
                deleteRdv(selectRdv.value)
                selectRdv.value= Rdv()
            }
            ListRdvEvent.OnGet -> {

            }
            ListRdvEvent.OnGetAll -> {
                loadRdv()
                loadContact()
            }
            ListRdvEvent.OnQueryClean -> {
                sendUIEvent(UIEvent.ShowAlertDialog(title = "Suppression",
                    message="Voulez-vous tout supprimer?",
                    action=Action.DELETE_ALL
                ))
            }
            ListRdvEvent.OnClean -> {
                cleanRdv()
                allRdvFlow.value.toMutableList().clear()
                selectRdv.value=Rdv()
            }
            else -> Unit
        }
    }


    private fun addRdv(rdv: Rdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.addRdv(rdv)
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
                useCase.updateRdv(rdv)
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
                useCase.deleteRdv(rdv.id)
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
                useCase.cleanRdv()
            }.onSuccess {
                isSateChanged.value = true
            }.onFailure {
                isSateChanged.value = false
            }
        }
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

