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
    val isSateChanged = MutableStateFlow(false)
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    val index = mutableStateOf(0)
    val nom: MutableState<String> = mutableStateOf("")
    val timestamp: MutableState<Long> = mutableStateOf(0L)
    val time = mutableStateOf("")

    init {
        // init time
        //init date
    }

    fun load() {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.getAllRdv()
            }.onFailure {
                allRdvFlow.value = emptyList()
                isSateChanged.value=true
            }.onSuccess {
                allRdvFlow.value = it
                isSateChanged.value=true
            }
        }
    }

    fun getRdv(id: Int) {
        viewModelScope.launch {

        }
    }

    fun addRdv(rdv: PostRdv) {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryRdv.addRdv(rdv)
            }.onSuccess {
                isSateChanged.value = true
            }.onFailure {
                isSateChanged.value = false
            }
        }
    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }
}