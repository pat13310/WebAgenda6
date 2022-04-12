package com.xenatronics.webagenda.domain.usecase.Contact

import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.repository.RepositoryContact
import com.xenatronics.webagenda.domain.model.ResponseSimple

class UpdateContact(val repository:RepositoryContact) {
    suspend operator fun invoke(contact: Contact):ResponseSimple =  repository.Update(contact)
}
