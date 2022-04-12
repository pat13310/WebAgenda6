package com.xenatronics.webagenda.domain.usecase.Contact

import com.xenatronics.webagenda.data.repository.RepositoryContact
import com.xenatronics.webagenda.domain.model.ResponseSimple

class CleanContact(val repository:RepositoryContact) {
    suspend operator fun invoke(): ResponseSimple= repository.Clean()
}
