package com.xenatronics.webagenda.domain.usecase.Contact

import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.repository.RepositoryContact

class GetContact(val repository: RepositoryContact) {
    suspend operator fun invoke(id:Int):List<Contact> = repository.Get(id)
}
