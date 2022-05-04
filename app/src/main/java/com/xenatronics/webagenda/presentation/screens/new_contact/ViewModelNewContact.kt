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
class ViewModelNewContact @Inject constructor(
    private val usesCase: UseCaseContact
) : ViewModel() {
    private val selectedContact = mutableStateOf(Contact())
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setCurrent(contact: Contact) {
        selectedContact.value = contact
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun OnEvent(event: NewContactEvent) {
        when (event) {
            is NewContactEvent.OnNew -> {
                addContact(selectedContact.value)
                sendUIEvent(UIEvent.Navigate(Screen.ListContactScreen.route))
            }
            is NewContactEvent.OnUpdate -> {
                updateContact(selectedContact.value)
                sendUIEvent(UIEvent.Navigate(Screen.ListContactScreen.route))
            }
            is NewContactEvent.OnBack -> {
                sendUIEvent(UIEvent.Navigate(Screen.ListContactScreen.route))
            }

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
}