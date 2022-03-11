package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ViewModelRegister:ViewModel() {
    val alias = mutableStateOf<String>("")
    val nom = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")
    val mail = mutableStateOf<String>("")
}