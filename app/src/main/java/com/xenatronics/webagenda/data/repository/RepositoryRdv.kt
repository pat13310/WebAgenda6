package com.xenatronics.webagenda.data.repository

import com.xenatronics.webagenda.common.util.Constants.ADD_RDV
import com.xenatronics.webagenda.common.util.Constants.CLEAR_RDV
import com.xenatronics.webagenda.common.util.Constants.DEL_RDV
import com.xenatronics.webagenda.common.util.Constants.GET_ALL_RDV
import com.xenatronics.webagenda.common.util.Constants.GET_RDV
import com.xenatronics.webagenda.common.util.Constants.UPDATE_RDV
import com.xenatronics.webagenda.data.PostID
import com.xenatronics.webagenda.data.network.KtorClient
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.model.ResponseSimple
import com.xenatronics.webagenda.domain.repository.RepositoryBaseRdv
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryRdv @Inject constructor() : RepositoryBaseRdv {

    override suspend fun GetAll(): List<Rdv> {
        return try {
            KtorClient.httpClient.get { url(GET_ALL_RDV) }
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
            // 3xx --
            println(e.message)
            emptyList()
        }
    }

    override suspend fun Get(id: Int): List<Rdv> {
        return try {
            KtorClient.httpClient.get {
                url(GET_RDV)
                parameter(key = "id", value = id)
            }
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

    override suspend fun Add(rdv: Rdv): ResponseSimple {
        return try {
            KtorClient.httpClient.post {
                url(ADD_RDV)
                contentType(ContentType.Application.Json)
                body = rdv
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimple("")
        }
    }

    override suspend fun Update(rdv: Rdv): ResponseSimple {
        return try {
            KtorClient.httpClient.put {
                url(UPDATE_RDV)
                contentType(ContentType.Application.Json)
                body = rdv
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimple("")
        }
    }

    override suspend fun Delete(id: Int): ResponseSimple {
        return try {
            KtorClient.httpClient.delete {
                url(DEL_RDV)
                contentType(ContentType.Application.Json)
                body = PostID(id)
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimple("")
        }
    }

    override suspend fun Clean(): ResponseSimple {
        return try {
            KtorClient.httpClient.get {
                url(CLEAR_RDV)
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimple("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimple("")
        }
    }
}
