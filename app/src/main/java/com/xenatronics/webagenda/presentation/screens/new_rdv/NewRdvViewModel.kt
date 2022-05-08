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
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.usecase.contact.UseCaseContact
import com.xenatronics.webagenda.domain.usecase.rdv.UseCaseRdv
import com.xenatronics.webagenda.domain.usecase.rdv.ValideRdv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewRdvViewModel @Inject constructor(
    private val useCase: UseCaseRdv,
    private val useCaseContact: UseCaseContact,

    ) : ViewModel() {
    val nom: MutableState<String> = mutableStateOf("")
    var calendar = mutableStateOf(Calendar.getInstance())
    // selected items
    var selectedRdv by mutableStateOf(Rdv())
    //var selectedContact by mutableStateOf(Contact())

    //var newRdvState = mutableStateOf(NewRdvState())

    val allContactFlow = MutableStateFlow<List<Contact>>(emptyList())
    private val _uiEvent = Channel<UIEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        Locale.setDefault(Locale.FRANCE)
    }

    fun setSelectRdv(rdv: Rdv) {
        selectedRdv = rdv
    }


    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun onEvent(event: NewRdvEvent) {
        when (event) {
            is NewRdvEvent.ChangedDate -> {
                selectedRdv.date = calendar.value.timeInMillis
            }
            is NewRdvEvent.ChangedTime -> {
                selectedRdv.date = calendar.value.timeInMillis
            }
            is NewRdvEvent.ChangedContact -> {
                selectedRdv.id_contact = event.contact.id
                selectedRdv.nom = event.contact.nom
                //selectedContact = event.contact
            }
            is NewRdvEvent.OnNew -> {
                val result = useCase.validateRdv.execute(selectedRdv)
                if (result==ValideRdv.ResultRdv.ValideRdv) {
                    addRdv(selectedRdv)
                    sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
                }
                else{
                    sendUIEvent(UIEvent.ShowSnackBar("Ok",result.getMessage()))
                }
            }
            is NewRdvEvent.OnUpdate -> {
                updateRdv(selectedRdv)
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

    private fun ValideRdv.ResultRdv.getMessage():String{
        return when(this){
            is ValideRdv.ResultRdv.EmptyName->{
                return "Rendez-vous vide"
            }
            is ValideRdv.ResultRdv.BadLength->{
                return "Le rendez-vous doit contenir minimum 3 caractères"
            }
            else -> ""
        }
    }
}
