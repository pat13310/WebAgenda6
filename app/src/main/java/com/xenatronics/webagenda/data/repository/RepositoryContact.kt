package com.xenatronics.webagenda.data.repository

import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.domain.model.PostID
import com.xenatronics.webagenda.domain.model.ResponseSimple

import com.xenatronics.webagenda.data.network.KtorClient
import com.xenatronics.webagenda.common.util.Constants
import com.xenatronics.webagenda.domain.repository.RepositoryBaseContact
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryContact @Inject constructor() :RepositoryBaseContact {

    override suspend fun GetAll(): List<Contact> {
        return try {
            KtorClient.httpClient.get { url(Constants.GET_ALL_CONTACT) }
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

    override suspend fun Get(id: Int): List<Contact> {
        return try {
            KtorClient.httpClient.get {
                url(Constants.GET_CONTACT)
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

    override suspend fun Add(contact: Contact): ResponseSimple {
        return try {
            KtorClient.httpClient.post() {
                url(Constants.ADD_CONTACT)
                contentType(ContentType.Application.Json)
                body = contact
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

    override suspend fun Update(contact: Contact): ResponseSimple {
        return try {
            KtorClient.httpClient.put() {
                url(Constants.UPDATE_CONTACT)
                contentType(ContentType.Application.Json)
                body = contact
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
            KtorClient.httpClient.delete() {
                url(Constants.DELETE_CONTACT)
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
                url(Constants.CLEAR_CONTACT)
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