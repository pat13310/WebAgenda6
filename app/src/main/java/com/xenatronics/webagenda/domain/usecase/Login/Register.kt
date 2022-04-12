package com.xenatronics.webagenda.domain.usecase.Login

import com.xenatronics.webagenda.common.util.Constants
import com.xenatronics.webagenda.data.network.KtorClient
import com.xenatronics.webagenda.data.repository.RepositoryLogin
import com.xenatronics.webagenda.domain.model.ResponseSimple
import com.xenatronics.webagenda.domain.model.User
import io.ktor.client.request.*
import io.ktor.http.*

class Register(val repository: RepositoryLogin) {
    suspend operator fun invoke(user: User):ResponseSimple = repository.Register(user)
}