package com.xenatronics.webagenda.presentation.screens.new_contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var state by mutableStateOf(NewContactState())

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
                state = state.copy(nom = event.nom)
            }
            is NewContactEvent.ChangedAdresse -> {
                state = state.copy(adresse = event.adresse)
            }
            is NewContactEvent.ChangedCp -> {
                state = state.copy(cp = event.cp)
            }
            is NewContactEvent.ChangedVille -> {
                state = state.copy(ville = event.ville)
            }
            is NewContactEvent.ChangedTel -> {
                state = state.copy(tel = event.tel)
            }
            is NewContactEvent.ChangedMail -> {
                state = state.copy(email = event.mail)
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
            val contact = Contact(
                id = state.id,
                nom = state.nom,
                cp = state.cp,
                ville = state.ville,
                mail = state.email,
                tel = state.tel
            )
            usesCase.addContact(contact = contact)
        }
    }

    private fun updateContact() {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = Contact(
                id = state.id,
                nom = state.nom,
                cp = state.cp,
                ville = state.ville,
                mail = state.email,
                tel = state.tel
            )
            usesCase.updateContact(contact = contact)
        }
    }

    fun setSelectContact(contact: Contact) {
        state = state.copy(
            id = contact.id,
            nom = contact.nom,
            adresse = contact.adresse,
            cp = contact.cp,
            ville = contact.ville,
            tel = contact.tel,
            email = contact.mail
        )
    }
}