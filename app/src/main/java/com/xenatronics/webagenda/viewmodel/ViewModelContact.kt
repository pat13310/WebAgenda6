package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.data.*
import com.xenatronics.webagenda.repository.RepositoryContact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelContact @Inject constructor() : ViewModel() {
    var allContactFlow = MutableStateFlow<List<ResponseContact>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val nom: MutableState<String> = mutableStateOf("")
    val adresse: MutableState<String> = mutableStateOf("")
    val ville: MutableState<String> = mutableStateOf("")
    val cp: MutableState<String> = mutableStateOf("")
    val tel: MutableState<String> = mutableStateOf("")
    val mail: MutableState<String> = mutableStateOf("")
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList

    init {

    }

    fun load() {
        viewModelScope.launch {
            _isLoading.value = true
            kotlin.runCatching {
                //delay(5000)
                RepositoryContact.getAllContact()

            }.onFailure {
                allContactFlow.value = emptyList()
                _isLoading.value = false
            }.onSuccess {
                allContactFlow.value = it
                _isLoading.value = false
            }
        }
    }

    fun AddContact(contact: PostContact) {
        viewModelScope.launch {
            RepositoryContact.addContact(contact = contact)
        }
    }

    fun DeleteContact(idContact: PostID) {
        viewModelScope.launch {
            RepositoryContact.deleteContact(idContact)
        }
    }


    fun UpdateContact(contact: Contact) {
        viewModelScope.launch {
            RepositoryContact.updateContact(contact = contact)
        }
    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }
}