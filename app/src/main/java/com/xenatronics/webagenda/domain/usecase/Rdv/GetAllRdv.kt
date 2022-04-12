package com.xenatronics.webagenda.domain.usecase.Rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.repository.RepositoryBaseRdv

class GetAllRdv(private val repository: RepositoryRdv) {
    suspend operator fun invoke(): List<Rdv> =repository.GetAll()
}