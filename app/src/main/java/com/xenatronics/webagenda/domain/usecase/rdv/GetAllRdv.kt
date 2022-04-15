package com.xenatronics.webagenda.domain.usecase.rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.Rdv

class GetAllRdv(private val repository: RepositoryRdv) {
    suspend operator fun invoke(): List<Rdv> =repository.GetAll()
}