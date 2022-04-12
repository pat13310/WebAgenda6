package com.xenatronics.webagenda.domain.usecase.Rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.ResponseSimple
import com.xenatronics.webagenda.domain.repository.RepositoryBaseRdv

class CleanRdv(private val repository: RepositoryRdv) {
    suspend operator fun invoke():ResponseSimple=repository.Clean()
}