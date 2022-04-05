package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.ResponseSimple
import com.xenatronics.webagenda.data.User
import com.xenatronics.webagenda.network.KtorClient
import com.xenatronics.webagenda.util.Constants
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

object RepositoryLogin {

    suspend fun login(user: User):ResponseSimple{
        return try {
            KtorClient.httpClient.post {
                url(Constants.LOGIN)
                contentType(ContentType.Application.Json)
                body=user
                //headers=HeadersBuilder()
                //parameter(key = "name", value = user.name.trim())
                //parameter(key = "password", value = user.password.trim())
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

    suspend fun register(user: User):ResponseSimple{
        return try {
            KtorClient.httpClient.post() {
                url(Constants.REGISTER)
                body=user
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

    suspend fun getAll():List<User>{
        return try {
            KtorClient.httpClient.get { url(Constants.LOGIN) }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            emptyList()
        }
    }
}