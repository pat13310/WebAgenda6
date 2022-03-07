package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.ResponseRDV
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.network.KtorClient
import com.xenatronics.webagenda.util.Constants
import io.ktor.client.features.*
import io.ktor.client.request.*

object RepositoryContact {
    suspend fun getListe():List<Rdv>{
        return try {
            KtorClient.httpClient.get{url(Constants.GET_ALL_CONTACT)}
        }
        catch (e: RedirectResponseException){
            // 3xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e: ClientRequestException){
            // 4xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e: ServerResponseException){
            // 5xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:Exception){
            // 3xx --
            println(e.message)
            emptyList()
        }
    }
    suspend fun clearListe(): ResponseRDV {
        return KtorClient.httpClient.use {
            it.get(Constants.CLEAR_CONTACT)
        }
    }
}