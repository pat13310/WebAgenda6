package com.xenatronics.webagenda.domain.usecase.contact

import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.data.repository.RepositoryContact

class GetContact(val repository: RepositoryContact) {
    suspend operator fun invoke(id:Int):List<Contact> = repository.Get(id)
}
