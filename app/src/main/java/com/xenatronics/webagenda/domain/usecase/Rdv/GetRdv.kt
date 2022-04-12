package com.xenatronics.webagenda.domain.usecase.Rdv

import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.repository.RepositoryBaseRdv

class GetRdv( private val repository: RepositoryRdv){
    suspend operator fun invoke(id:Int):List<Rdv> = repository.Get(id)
}

