package com.xenatronics.webagenda.navigation

sealed class Screen(val route:String){
    object AddScreen:Screen("add")
    object ContactScreen:Screen("contact")
    object CardScreen:Screen("list")
    object LoginScreen:Screen("login")
    object RegisterScreen:Screen("register")
    object SplashScreen:Screen("splash")
}
