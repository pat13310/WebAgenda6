package com.xenatronics.webagenda.common.navigation

sealed class Screen(val route:String){
    object NewRdvScreen:Screen("new_rdv")
    object NewContactScreen:Screen("new_contact")
    object ListContactScreen:Screen("list_contact")
    object ListRdvScreen:Screen("list")
    object LoginScreen:Screen("login")
    object RegisterScreen:Screen("register")
    object SplashScreen:Screen("splash")
}
