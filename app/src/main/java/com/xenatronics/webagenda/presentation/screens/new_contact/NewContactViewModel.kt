package com.xenatronics.webagenda.presentation.screens.new_contact

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.common.events.NewContactEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.domain.usecase.contact.UseCaseContact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewContactViewModel @Inject constructor(
    private val usesCase: UseCaseContact
) : ViewModel() {
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val selectedContact = mutableStateOf(Contact())


    fun onEvent(event: NewContactEvent) {
        when (event) {
            is NewContactEvent.OnNew -> {
                addContact()
                sendUIEvent(UIEvent.Navigate(Screen.ListContactScreen.route))
            }
            is NewContactEvent.OnUpdate -> {
                updateContact()
                sendUIEvent(UIEvent.Navigate(Screen.ListContactScreen.route))
            }
            is NewContactEvent.OnBack -> {
                sendUIEvent(UIEvent.Navigate(Screen.ListContactScreen.route))
            }
            is NewContactEvent.ChangedNom -> {
                selectedContact.value = selectedContact.value.copy(nom = event.nom)
            }
            is NewContactEvent.ChangedAdresse -> {
                selectedContact.value = selectedContact.value.copy(adresse = event.adresse)
            }
            is NewContactEvent.ChangedCp -> {
                selectedContact.value = selectedContact.value.copy(cp = event.cp)
            }
            is NewContactEvent.ChangedVille -> {
                selectedContact.value = selectedContact.value.copy(ville = event.ville)
            }
            is NewContactEvent.ChangedTel -> {
                selectedContact.value = selectedContact.value.copy(tel = event.tel)
            }
            is NewContactEvent.ChangedMail -> {
                selectedContact.value = selectedContact.value.copy(mail = event.mail)
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            usesCase.addContact(contact = contact)
        }
    }

    private fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            usesCase.updateContact(contact = contact)
        }
    }

    private fun addContact() {
        viewModelScope.launch(Dispatchers.IO) {
            usesCase.addContact(contact = selectedContact.value)
        }
    }

    private fun updateContact() {
        viewModelScope.launch(Dispatchers.IO) {
            usesCase.updateContact(contact = selectedContact.value)
        }
    }

    fun setSelectContact(contact: Contact) {
        selectedContact.value = contact
    }
}