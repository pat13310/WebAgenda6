package com.xenatronics.webagenda.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.PostID
import com.xenatronics.webagenda.data.repository.RepositoryContact
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.domain.usecase.Contact.UseCaseContact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelContact @Inject constructor(
    private val useCaseContact: UseCaseContact
) : ViewModel() {
    val allContactFlow = MutableStateFlow<List<Contact>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val id: MutableState<Int> = mutableStateOf(0)
    val nom: MutableState<String> = mutableStateOf("")
    private val adresse: MutableState<String> = mutableStateOf("")
    private val ville: MutableState<String> = mutableStateOf("")
    private val cp: MutableState<String> = mutableStateOf("")
    private val tel: MutableState<String> = mutableStateOf("")
    private val mail: MutableState<String> = mutableStateOf("")
    val action = mutableStateOf(Action.NO_ACTION)
    val selectedItem = mutableStateOf(Contact())


    private val _isSateChanged = MutableStateFlow(false)
    val isStateChanged: StateFlow<Boolean> = _isSateChanged

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Agenda", "Load")
            _isLoading.value = true
            kotlin.runCatching {
                useCaseContact.getAllContact()
            }.onFailure {
                allContactFlow.value = emptyList()
                _isLoading.value = false
            }.onSuccess { list ->
                allContactFlow.value = list.sortedBy { contact -> contact.nom }
                _isLoading.value = false
            }
        }
    }

    fun handleContactAction(action: Action) {
        _isSateChanged.value = false
        when (action) {
            Action.ADD -> {
                addContact()
            }
            Action.DELETE -> {
                deleteContact()
            }
            Action.UPDATE -> {
                updateContact()
            }
            Action.DELETE_ALL -> {
                cleanContact()
            }
            Action.UNDO -> {
                addContact()
            }
            else -> {
                Log.d("Agenda", "aucune action")
            }
        }
        _isSateChanged.value = true
    }


    private fun cleanContact() {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContact.cleanContact()
        }
    }


    private fun addContact() {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = Contact(
                nom = nom.value,
                adresse = adresse.value,
                cp = cp.value,
                ville = ville.value,
                tel = tel.value,
                mail = mail.value
            )
            useCaseContact.addContact(contact = contact)
        }
    }

    private fun deleteContact() {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContact.deleteContact(id.value)
        }
    }

    private fun updateContact() {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = Contact(
                id = id.value,
                nom = nom.value,
                adresse = adresse.value,
                cp = cp.value,
                ville = ville.value,
                tel = tel.value,
                mail = mail.value
            )
            useCaseContact.updateContact(contact = contact)
        }
    }

    fun updateFields(contact: Contact) {
        id.value = contact.id
        nom.value = contact.nom
        adresse.value = contact.adresse
        cp.value = contact.cp
        ville.value = contact.ville
        tel.value = contact.tel
        mail.value = contact.mail
    }
}