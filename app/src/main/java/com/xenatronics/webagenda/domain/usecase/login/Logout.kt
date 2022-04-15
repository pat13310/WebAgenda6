package com.xenatronics.webagenda.domain.usecase.login


import com.xenatronics.webagenda.data.repository.RepositoryLogin
import com.xenatronics.webagenda.domain.model.ResponseSimple
import com.xenatronics.webagenda.domain.model.User

class Logout(val repository:RepositoryLogin) {
    suspend operator fun invoke(user: User):ResponseSimple=repository.Logout(user)
}