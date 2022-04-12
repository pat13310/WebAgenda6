package com.xenatronics.webagenda.domain.usecase.Contact

import com.xenatronics.webagenda.data.PostID
import com.xenatronics.webagenda.data.repository.RepositoryContact
import com.xenatronics.webagenda.domain.model.ResponseSimple

class DeleteContact(val repository:RepositoryContact) {
    suspend operator fun invoke(id:Int):ResponseSimple= repository.Delete(id)
}
