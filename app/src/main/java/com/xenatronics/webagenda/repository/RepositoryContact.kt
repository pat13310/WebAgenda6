package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.PostID
import com.xenatronics.webagenda.data.ResponseSimpleContact
import com.xenatronics.webagenda.network.KtorClient
import com.xenatronics.webagenda.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

@Module
@InstallIn(SingletonComponent::class)
object RepositoryContact  {
    @Provides
    suspend fun getAllContact(): List<Contact> {
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


    suspend fun getContact(id: Int): List<Contact> {
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

    suspend fun addContact(contact: Contact): ResponseSimpleContact {
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
            ResponseSimpleContact("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleContact(e.message.toString())
        }
    }

    suspend fun updateContact(contact: Contact): ResponseSimpleContact {
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
            ResponseSimpleContact("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleContact("")
        }
    }

    suspend fun deleteContact(postId: PostID): ResponseSimpleContact {
        return try {
            KtorClient.httpClient.delete() {
                url(Constants.DELETE_CONTACT)
                contentType(ContentType.Application.Json)
                body = postId
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleContact("")
        }
    }

    suspend fun clearContact(): ResponseSimpleContact {
        return try {
            KtorClient.httpClient.get {
                url(Constants.CLEAR_CONTACT)
            }
        }
        catch (e: RedirectResponseException) {
            // 3xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: ClientRequestException) {
            // 4xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: ServerResponseException) {
            // 5xx --
            println(e.response.status.description)
            ResponseSimpleContact("")
        }
        catch (e: Exception) {
            // xxx --
            println(e.message)
            ResponseSimpleContact("")
        }
    }
}