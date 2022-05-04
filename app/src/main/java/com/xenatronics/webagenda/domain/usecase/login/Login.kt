package com.xenatronics.webagenda.domain.usecase.login

import com.xenatronics.webagenda.data.repository.RepositoryLogin
import com.xenatronics.webagenda.domain.model.ResponseSimple
import com.xenatronics.webagenda.domain.model.Credentials


class Login(val repository: RepositoryLogin) {
    suspend operator fun invoke(user: Credentials): ResponseSimple =repository.Login(user)
}