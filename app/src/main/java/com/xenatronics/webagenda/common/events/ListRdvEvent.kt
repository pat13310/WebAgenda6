package com.xenatronics.webagenda.common.events

import android.app.Person
import com.xenatronics.webagenda.domain.model.Contact

sealed class ListRdvEvent {
    object OnAdd : ListRdvEvent()
    object OnDelete : ListRdvEvent()
    object OnUpdate : ListRdvEvent()
    object OnGet : ListRdvEvent()
    object OnGetAll : ListRdvEvent()
    object OnClean : ListRdvEvent()
    object OnNothing : ListRdvEvent()
    object OnNew : ListRdvEvent()
    object OnEdit : ListRdvEvent()
    object OnQueryDelete:ListRdvEvent()
    object OnQueryClean:ListRdvEvent()
    object OnLogout:ListRdvEvent()
}


sealed class NewRdvEvent {
    object OnNew : NewRdvEvent()
    object OnUpdate : NewRdvEvent()
    object OnBack : NewRdvEvent()
    data class ChangedContact(val contact: Contact):NewRdvEvent()
    data class ChangedDate(val date:Long):NewRdvEvent()
    data class ChangedTime(val time:Long):NewRdvEvent()
}
