package com.xenatronics.webagenda.common.util

import com.xenatronics.webagenda.domain.model.Credentials

interface Authenticator {
    suspend fun login(user: Credentials)
    suspend fun register(user: Credentials)
    suspend fun logout(user: Credentials)
}


class RestApiBaseAuth : Authenticator {

    override suspend fun login(user: Credentials) {

        //RepositoryLogin.Login(user)
    }

    override suspend fun register(user: Credentials) {
        //RepositoryLogin.Register(user)
    }

    override suspend fun logout(user: Credentials) {

    }
}

class FireBaseAuth : Authenticator {
    override suspend fun login(user: Credentials) {

    }

    override suspend fun register(user: Credentials) {

    }

    override suspend fun logout(user: Credentials) {

    }
}

class FakeBaseAuth : Authenticator {
    override suspend fun login(user: Credentials) {

    }

    override suspend fun register(user: Credentials) {

    }

    override suspend fun logout(user: Credentials) {

    }
}