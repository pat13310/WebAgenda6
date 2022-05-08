package com.xenatronics.webagenda.presentation.screens.new_rdv

data class NewRdvState(
    val id: Int = 0,
    val name: String = "",
    val date: Long = 0,
    val id_contact: Int = -1,
    val adresse: String = "",
    val cp: String = "",
    val ville: String = "",
    val tel: String = "",
    val mail: String = "",
    val dateString: String = "",
    val timeString: String = ""
)


