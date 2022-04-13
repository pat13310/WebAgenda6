package com.xenatronics.webagenda.common.events

sealed class ListContactEvent{
        object OnAdd:ListContactEvent()
        object OnDelete:ListContactEvent()
        object OnUpdate:ListContactEvent()
        object OnGet:ListContactEvent()
        object OnGetAll:ListContactEvent()
        object OnClean:ListContactEvent()
}

sealed class NewContactEvent {
        object OnNew : NewContactEvent()
        object OnUpdate : NewContactEvent()
        object OnBack : NewContactEvent()
}
