package com.xenatronics.webagenda.domain.usecase.rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.ResponseSimple

class CleanRdv(private val repository: RepositoryRdv) {
    suspend operator fun invoke():ResponseSimple=repository.Clean()
}