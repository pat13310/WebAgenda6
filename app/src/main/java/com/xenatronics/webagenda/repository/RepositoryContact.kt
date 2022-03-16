package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.ResponseContact
import com.xenatronics.webagenda.data.PostContact
import com.xenatronics.webagenda.data.ResponseSimpleContact
import com.xenatronics.webagenda.network.KtorClient
import com.xenatronics.webagenda.util.Constants
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

object RepositoryContact {
    suspend fun getAllContact(): List<ResponseContact> {
        return try {
            KtorClient.httpClient.get { url(Constants.GET_ALL_CONTACT) }
        } catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            emptyList()
        } catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            emptyList()
        } catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            emptyList()
        } catch (e: Exception) {
            // xxx --
            println(e.message)
            emptyList()
        }
    }

    suspend fun getContact(id: Int):List<ResponseContact> {
        return try {
            KtorClient.httpClient.get {
                url(Constants.GET_CONTACT)
                parameter(key="id", value = id)
            }
        } catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            emptyList()
        } catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            emptyList()
        } catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            emptyList()
        } catch (e: Exception) {
            // 3xx --
            println(e.message)
            emptyList()
        }
    }

    suspend fun addContact(contact:PostContact): ResponseSimpleContact {
        return try {
            KtorClient.httpClient.post() {
                url(Constants.ADD_CONTACT)
                contentType(ContentType.Application.Json)
                body=contact
            }
        } catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        } catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        } catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        } catch (e: Exception) {
            // 3xx --
            println(e.message)
            ResponseSimpleContact("")
        }
    }
    suspend fun updateContact(contact: Contact):ResponseSimpleContact{
        return try {
            KtorClient.httpClient.put() {
                url(Constants.UPDATE_CONTACT)
                contentType(ContentType.Application.Json)
                body=contact
            }
        } catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        } catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        } catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        } catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleContact("")
        }
    }
    suspend fun deleteContact(id:Int):ResponseSimpleContact{
        return KtorClient.httpClient.use {
            it.delete(Constants.DELETE_CONTACT){
                parameter("id",id)
            }
        }
    }

    suspend fun clearContact(): ResponseSimpleContact {
        return KtorClient.httpClient.use {
            it.get(Constants.CLEAR_CONTACT)
        }
    }
}