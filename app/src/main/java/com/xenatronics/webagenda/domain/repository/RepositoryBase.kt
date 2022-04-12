package com.xenatronics.webagenda.domain.repository

import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.model.ResponseSimple
import com.xenatronics.webagenda.domain.model.User

interface RepositoryBaseContact {
    suspend fun GetAll():List<Contact>
    suspend fun Get(id:Int):List<Contact>
    suspend fun Add(contact: Contact):ResponseSimple
    suspend fun Update(contact: Contact):ResponseSimple
    suspend fun Delete(id:Int):ResponseSimple
    suspend fun Clean():ResponseSimple
}

interface RepositoryBaseRdv {
    suspend fun GetAll():List<Rdv>
    suspend fun Get(id:Int):List<Rdv>
    suspend fun Add(rdv: Rdv):ResponseSimple
    suspend fun Update(rdv: Rdv):ResponseSimple
    suspend fun Delete(id:Int):ResponseSimple
    suspend fun Clean():ResponseSimple
}

interface RepositoryBaseLogin{
    suspend fun Login(user: User):ResponseSimple
    suspend fun Logout(user: User):ResponseSimple
    suspend fun Register(user: User):ResponseSimple
}