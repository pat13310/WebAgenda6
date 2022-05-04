package com.xenatronics.webagenda.domain.usecase.contact

import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.data.repository.RepositoryContact

class GetAllContact(val repository:RepositoryContact){
    suspend operator fun invoke() : List<Contact> = repository.GetAll()
}
