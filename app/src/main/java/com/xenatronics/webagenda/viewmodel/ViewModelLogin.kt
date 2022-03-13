package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin @Inject constructor():ViewModel() {
    val nom = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")
}