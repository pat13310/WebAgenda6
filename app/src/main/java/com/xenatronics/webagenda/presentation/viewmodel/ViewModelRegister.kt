package com.xenatronics.webagenda.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelRegister @Inject constructor() :ViewModel() {
    val alias = mutableStateOf<String>("")
    val nom = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")
    val mail = mutableStateOf<String>("")
}