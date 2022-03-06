package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.Rdv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelContact {
    val allRdvFlow = MutableStateFlow<List<Rdv>>(emptyList())
    val addRdvFlow = MutableStateFlow<Boolean>(false)
    val index = mutableStateOf(0)
    val nom: MutableState<String> = mutableStateOf("")
    val timestamp: MutableState<Long> = mutableStateOf(0L)
    val adresse: MutableState<String> = mutableStateOf("")
    val ville: MutableState<String> = mutableStateOf("")
    val cp: MutableState<String> = mutableStateOf("")
    val tel: MutableState<String> = mutableStateOf("")
    val mail: MutableState<String> = mutableStateOf("")

    init {

    }

    fun AddContact(contact: Contact){

    }
}