package com.xenatronics.webagenda.common.events

sealed class ListContactEvent{
        object OnAdd:ListContactEvent()
        object OnQueryDelete:ListContactEvent()
        object OnDelete:ListContactEvent()
        object OnUpdate:ListContactEvent()
        object OnGet:ListContactEvent()
        object OnGetAll:ListContactEvent()
        object OnClean:ListContactEvent()
        object OnUndo:ListContactEvent()
        object OnValidate : ListContactEvent()
        object OnBack : ListContactEvent()
}

sealed class NewContactEvent {
        object OnNew : NewContactEvent()
        object OnUpdate : NewContactEvent()
        object OnBack : NewContactEvent()
        data class ChangedNom(val nom:String):NewContactEvent()
        data class ChangedAdresse(val adresse:String):NewContactEvent()
        data class ChangedCp(val cp:String):NewContactEvent()
        data class ChangedVille(val ville: String):NewContactEvent()
        data class ChangedTel(val tel:String):NewContactEvent()
        data class ChangedMail(val mail:String):NewContactEvent()
}


