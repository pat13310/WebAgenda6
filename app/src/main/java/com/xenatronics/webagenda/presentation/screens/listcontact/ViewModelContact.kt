package com.xenatronics.webagenda.presentation.screens.listcontact

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.xenatronics.webagenda.common.events.ListContactEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.usecase.contact.UseCaseContact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelContact @Inject constructor(
    private val useCaseContact: UseCaseContact
) : ViewModel() {

    val allContactFlow = MutableStateFlow<List<Contact>>(emptyList())
    val id: MutableState<Int> = mutableStateOf(0)
    val nom: MutableState<String> = mutableStateOf("")
    val action = mutableStateOf(Action.NO_ACTION)
    val selectedContact = mutableStateOf(Contact())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun loadContact() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCaseContact.getAllContact()
            }.onFailure {
                allContactFlow.value = emptyList()
            }.onSuccess { list ->
                allContactFlow.value = list.sortedBy { contact -> contact.nom }
            }
        }
    }


    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


    fun OnEvent(event: ListContactEvent) {
        when (event) {
            is ListContactEvent.OnValidate -> {
                if (selectedContact.value.id>0) {
                    val rdvSelected = Rdv(nom = selectedContact.value.nom, date =0L, id_contact = selectedContact.value.id)
                    val rdv= Gson().toJson(rdvSelected)
                    sendUIEvent(UIEvent.Navigate(Screen.NewRdvScreen.route + "/$rdv"))
                }
            }
            is ListContactEvent.OnAdd -> {
                addContact()
            }
            is ListContactEvent.OnQueryDelete -> {
                deleteContact()
                sendUIEvent(
                    UIEvent.ShowSnackBar(
                        action = "Rétablir",
                        message = "Suppression de ${selectedContact.value.nom}"
                    )
                )
            }
            is ListContactEvent.OnDelete -> {
                deleteContact()
            }
            is ListContactEvent.OnUndo -> {
                addContact()
            }
            else -> Unit
        }
    }


    private fun cleanContact() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCaseContact.cleanContact()
            }.onSuccess {

            }.onFailure {

            }
        }
    }


    private fun addContact() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCaseContact.addContact(contact = selectedContact.value)
            }.onSuccess {
                loadContact()
            }.onFailure {

            }
        }
    }


    private fun deleteContact() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCaseContact.deleteContact(selectedContact.value.id)
            }.onSuccess {
                loadContact()
            }.onFailure {

            }
        }
    }
}