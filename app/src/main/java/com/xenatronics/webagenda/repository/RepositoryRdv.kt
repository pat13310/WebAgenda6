package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.*
import com.xenatronics.webagenda.network.KtorClient
import com.xenatronics.webagenda.util.Constants.ADD_RDV
import com.xenatronics.webagenda.util.Constants.CLEAR_RDV
import com.xenatronics.webagenda.util.Constants.DEL_RDV
import com.xenatronics.webagenda.util.Constants.GET_ALL_RDV
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

    suspend fun getRdv(id: Int): List<ResponseRDV> {
        return try {
            KtorClient.httpClient.get {
                url(GET_ALL_RDV)
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

    suspend fun clearRdv(): ResponseSimpleRdv {
        return try {
            KtorClient.httpClient.get {
                url(CLEAR_RDV)
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleRdv("")
        }
    }

    suspend fun deleteRdv(id: PostID): ResponseSimpleRdv {
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
            ResponseSimpleRdv("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleRdv("")
        }
    }

    suspend fun addRdv(rdv: PostRdv): ResponseSimpleRdv {
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
            ResponseSimpleRdv("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleRdv("")
        }
    }

    suspend fun updateRdv(rdv: PostRdv): ResponseSimpleRdv {
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
            ResponseSimpleRdv("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleRdv("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleRdv("")
        }
    }
}
