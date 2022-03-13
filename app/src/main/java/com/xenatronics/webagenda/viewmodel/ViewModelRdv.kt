package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.data.PostRdv
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.repository.RepositoryRdv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelRdv @Inject constructor() : ViewModel() {
    val allRdvFlow = MutableStateFlow<List<Rdv>>(emptyList())
    val addRdvFlow = MutableStateFlow<Boolean>(false)
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    val index = mutableStateOf(0)
    val nom: MutableState<String> = mutableStateOf("")
    val timestamp: MutableState<Long> = mutableStateOf(0L)
    val adresse: MutableState<String> = mutableStateOf("")
    val ville: MutableState<String> = mutableStateOf("")
    val cp: MutableState<String> = mutableStateOf("")
    val tel: MutableState<String> = mutableStateOf("")
    val mail: MutableState<String> = mutableStateOf("")

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.getAllRdv()
            }.onFailure {
                allRdvFlow.value = emptyList()
            }.onSuccess {
                allRdvFlow.value = it
            }
        }
    }

    fun GetRdv(id: Int) {
        viewModelScope.launch {

        }
    }

    fun AddRdv(rdv: PostRdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.addRdv(rdv)
            }.onSuccess {
                addRdvFlow.value = true
            }.onFailure {
                addRdvFlow.value = false
            }
        }
    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }
}