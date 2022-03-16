package com.xenatronics.webagenda.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun checkInternetAvailable(context:Context):Boolean{
    val connectManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
        val activenetwork=connectManager.activeNetwork?:return false
        val capabilities=connectManager.getNetworkCapabilities(activenetwork)?:return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
            else ->false
        }
    }
    return connectManager.activeNetworkInfo?.isAvailable?:false
}