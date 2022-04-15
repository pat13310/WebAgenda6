package com.xenatronics.webagenda.domain.usecase.rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.ResponseSimple

class DeleteRdv(private val repository: RepositoryRdv) {
    suspend operator fun invoke(id: Int): ResponseSimple = repository.Delete(id)
}