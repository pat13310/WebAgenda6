package com.xenatronics.webagenda.domain.usecase.rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.model.ResponseSimple

class AddRdv(private val repository: RepositoryRdv) {
    suspend operator fun invoke(rdv: Rdv): ResponseSimple {
        return repository.Add(rdv)
    }
}