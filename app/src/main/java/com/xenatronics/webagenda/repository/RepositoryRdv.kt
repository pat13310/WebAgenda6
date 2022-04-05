package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.*

import com.xenatronics.webagenda.network.KtorClient
import com.xenatronics.webagenda.util.Constants.ADD_RDV
import com.xenatronics.webagenda.util.Constants.CLEAR_RDV
import com.xenatronics.webagenda.util.Constants.DEL_RDV
import com.xenatronics.webagenda.util.Constants.GET_ALL_RDV
import com.xenatronics.webagenda.util.Constants.GET_RDV
import com.xenatronics.webagenda.util.Constants.UPDATE_RDV
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

object RepositoryRdv {

    suspend fun getAllRdv(): List<Rdv> {
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

    suspend fun getRdv(id: Int): List<Rdv> {
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
            // 3xx --
            println(e.message)
            emptyList()
        }
    }

    suspend fun clearRdv(): ResponseSimple {
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

    suspend fun deleteRdv(id: PostID): ResponseSimple {
        return try {
            KtorClient.httpClient.delete {
                url(DEL_RDV)
                contentType(ContentType.Application.Json)
                body = id
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

    suspend fun addRdv(rdv:Rdv): ResponseSimple {
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

    suspend fun updateRdv(rdv: Rdv): ResponseSimple {
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
}


