package com.xenatronics.webagenda.presentation.screens.new_contact

data class NewContactState(
    val id: Int = 0,
    var nom: String = "",
    val adresse: String = "",
    val cp: String = "",
    val ville: String = "",
    val tel: String = "",
    val email: String = ""
)
