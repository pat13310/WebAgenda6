package com.xenatronics.webagenda.repository

import com.xenatronics.webagenda.data.CardResponse
import com.xenatronics.webagenda.data.PostRequest
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.network.KtorClient
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.cio.*

object RepositoryRdv {
    const val IP="192.168.1.68"
    private const val URL="http://$IP/api/V21/agenda/tasks"
    private const val GET_TASKS="$URL/all"
    private const val CLEAR_TASKS="$URL/clear"
    private const val ADD_TASK="$URL/add"
    private const val DEL_TASK="$URL/delete"

    suspend fun getListe():List<Rdv>{
        return try {
            KtorClient.httpClient.get{url(GET_TASKS)}
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
    suspend fun clearListe():CardResponse{
        return KtorClient.httpClient.use {
            it.get(CLEAR_TASKS)
        }
    }

    suspend fun clearRdv(id: Int): CardResponse {
        return KtorClient.httpClient.use {
            it.get("$DEL_TASK/$id")
        }
    }

    suspend fun addRdv(rdv: PostRequest):List<CardResponse>{
        return try {
            KtorClient.httpClient.post{
                url(ADD_TASK)
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
