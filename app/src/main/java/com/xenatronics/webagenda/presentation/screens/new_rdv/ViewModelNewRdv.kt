package com.xenatronics.webagenda.presentation.screens.new_rdv

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.common.events.NewRdvEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.getDateFormatter
import com.xenatronics.webagenda.common.util.getTimeFormatter
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.usecase.contact.UseCaseContact
import com.xenatronics.webagenda.domain.usecase.rdv.UseCaseRdv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewModelNewRdv @Inject constructor(
    private val useCase: UseCaseRdv,
    private val useCaseContact: UseCaseContact,

    ) : ViewModel() {
    val nom: MutableState<String> = mutableStateOf("")
    private var timestamp = mutableStateOf(0L)
    var calendar = mutableStateOf(Calendar.getInstance())
    private var time = mutableStateOf("")
    var date = mutableStateOf("")
    // selected items
    var selectRdv = mutableStateOf(Rdv())
    val selectContact = mutableStateOf(Contact())

    val allContactFlow = MutableStateFlow<List<Contact>>(emptyList())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(NewRdvState())

    init {
        timeSetup()
    }

    private fun timeSetup() {
        Locale.setDefault(Locale.FRANCE)
        // init time
        timestamp.value = calendar.value.timeInMillis
        state.dateString= getDateFormatter(timestamp.value)
        date.value = getDateFormatter(timestamp.value)
        //init date
        state.timeString= getTimeFormatter(timestamp.value)
        time.value = getTimeFormatter(timestamp.value)
    }

    fun setSelectRdv(rdv: Rdv) {
        //selectRdv.value = rdv
        state.rdv=rdv
    }

    fun setSelectContact(contact: Contact) {
        //selectContact.value = contact
        //selectRdv.value.id_contact = selectContact.value.id
        //selectRdv.value.nom = selectContact.value.nom
        state.contact=contact
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun onEvent(event: NewRdvEvent) {
        when (event) {
            is NewRdvEvent.ChangedDate->{
                state= state.copy(dateString = event.date)
            }
            is NewRdvEvent.ChangedTime->{
                state= state.copy(timeString = event.time)
            }
            is NewRdvEvent.ChangedContact->{
                state.rdv?.nom = event.contact.nom
                state= state.copy(contact = event.contact)
                //setSelectContact(state.contact!!)
            }
            is NewRdvEvent.OnNew -> {
                addRdv(state.rdv!!)
                //addRdv(selectRdv.value)
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }
            is NewRdvEvent.OnUpdate -> {
                updateRdv(state.rdv!!)
                //updateRdv(selectRdv.value)
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }
            is NewRdvEvent.OnBack -> {
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }
        }
    }

    private fun addRdv(rdv: Rdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.addRdv(rdv)
            }.onSuccess {
                //isSateChanged.value = true
            }.onFailure {
                //isSateChanged.value = false
            }
        }
    }

    private fun updateRdv(rdv: Rdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.updateRdv(rdv)
                //RepositoryRdv.updateRdv(rdv)
            }.onSuccess {
                //isSateChanged.value = true
            }.onFailure {
                //isSateChanged.value = false
            }
        }
    }

    fun loadContact() {
        Log.d("Rdv", "load contact")
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
}