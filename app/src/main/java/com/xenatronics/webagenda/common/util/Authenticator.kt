package com.xenatronics.webagenda.common.util

import com.xenatronics.webagenda.domain.model.User
import com.xenatronics.webagenda.data.repository.RepositoryLogin

interface Authenticator {
    suspend fun login(user: User)
    suspend fun register(user: User)
    suspend fun logout(user: User)
}


class RestApiBaseAuth : Authenticator {

    override suspend fun login(user: User) {

        //RepositoryLogin.Login(user)
    }

    override suspend fun register(user: User) {
        //RepositoryLogin.Register(user)
    }

    override suspend fun logout(user: User) {

    }
}

class FireBaseAuth : Authenticator {
    override suspend fun login(user: User) {

    }

    override suspend fun register(user: User) {

    }

    override suspend fun logout(user: User) {

    }
}

class FakeBaseAuth : Authenticator {
    override suspend fun login(user: User) {

    }

    override suspend fun register(user: User) {

    }

    override suspend fun logout(user: User) {

    }
}