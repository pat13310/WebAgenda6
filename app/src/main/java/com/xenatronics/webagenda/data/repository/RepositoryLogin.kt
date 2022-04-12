package com.xenatronics.webagenda.data.repository

import com.xenatronics.webagenda.common.util.Constants
import com.xenatronics.webagenda.data.network.KtorClient
import com.xenatronics.webagenda.domain.model.ResponseSimple
import com.xenatronics.webagenda.domain.model.User
import com.xenatronics.webagenda.domain.repository.RepositoryBaseLogin
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryLogin @Inject constructor() : RepositoryBaseLogin {

    override suspend fun Login(user: User): ResponseSimple {
        return try {
            KtorClient.httpClient.post {
                url(Constants.LOGIN)
                contentType(ContentType.Application.Json)
                body = user
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimple()
        }
    }

    override suspend fun Register(user: User): ResponseSimple {
        return try {
            KtorClient.httpClient.post {
                url(Constants.REGISTER)
                body = user
                contentType(ContentType.Application.Json)
//                parameter(key="name", value=user.name)
//                parameter(key="password", value=user.password)
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimple()
        }
    }


    override suspend fun Logout(user: User): ResponseSimple {
        return try {
            KtorClient.httpClient.post {
                url(Constants.LOGOUT)
                body = user
                contentType(ContentType.Application.Json)
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimple()
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimple()
        }
    }

}