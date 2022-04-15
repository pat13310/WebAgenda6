package com.xenatronics.webagenda.domain.usecase.rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.Rdv

class GetRdv( private val repository: RepositoryRdv){
    suspend operator fun invoke(id:Int):List<Rdv> = repository.Get(id)
}

