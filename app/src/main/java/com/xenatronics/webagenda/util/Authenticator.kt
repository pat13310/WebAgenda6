package com.xenatronics.webagenda.util

import androidx.compose.runtime.LaunchedEffect
import com.xenatronics.webagenda.data.User
import com.xenatronics.webagenda.repository.RepositoryLogin

interface Authenticator {
    suspend fun login(user: User)
    suspend fun register(user: User)
    suspend fun logout(user: User)
}


class RestApiBaseAuth : Authenticator {

    override suspend fun login(user: User) {
        RepositoryLogin.login(user)
    }

    override suspend fun register(user: User) {
        RepositoryLogin.register(user)
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