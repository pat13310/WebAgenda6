package com.xenatronics.webagenda.presentation.screens.new_rdv

import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.domain.model.Rdv

data class NewRdvState(
    var rdv:Rdv?=null,
    var contact: Contact? = null,
    val date: Long = 0,
    var dateString: String = "",
    var timeString: String = ""
)


