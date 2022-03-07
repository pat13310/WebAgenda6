package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.ResponseRDV
import com.xenatronics.webagenda.data.PostRequest
import com.xenatronics.webagenda.data.Rdv
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

    suspend fun getListe():List<Rdv>{
        return try {
            KtorClient.httpClient.get{url(GET_ALL_RDV)}
        }
        catch (e: RedirectResponseException){
            // 3xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:ClientRequestException){
            // 4xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:ServerResponseException){
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
    suspend fun clearListe():ResponseRDV{
        return KtorClient.httpClient.use {
            it.get(CLEAR_RDV)
        }
    }

    suspend fun clearRdv(id: Int): ResponseRDV {
        return KtorClient.httpClient.use {
            it.get("$DEL_RDV/$id")
        }
    }

    suspend fun addRdv(rdv: PostRequest):List<ResponseRDV>{
        return try {
            KtorClient.httpClient.post{
                url(ADD_RDV)
                contentType(ContentType.Application.Json)
                body=rdv
            }
        }
        catch (e: RedirectResponseException){
            // 3xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:ClientRequestException){
            // 4xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:ServerResponseException){
            // 5xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:Exception){
            // xxx --
            println(e.message)
            emptyList()
        }

        }

    suspend fun updateRdv(rdv: PostRequest):List<ResponseRDV>{
        return try {
            KtorClient.httpClient.put{
                url(UPDATE_RDV)
                contentType(ContentType.Application.Json)
                body=rdv
            }
        }
        catch (e: RedirectResponseException){
            // 3xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:ClientRequestException){
            // 4xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:ServerResponseException){
            // 5xx --
            println(e.response.status.description)
            emptyList()
        }
        catch (e:Exception){
            // xxx --
            println(e.message)
            emptyList()
        }

    }
    }
