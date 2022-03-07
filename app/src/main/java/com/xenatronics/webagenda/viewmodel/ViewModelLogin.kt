package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ViewModelLogin:ViewModel() {

    val nom = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")

}