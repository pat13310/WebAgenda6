package com.xenatronics.webagenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xenatronics.webagenda.activities.*
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.ui.theme.WebAgendaTheme
import com.xenatronics.webagenda.viewmodel.ViewmodelRdv

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WebAgendaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.AddScreen.route
                ) {
                    composable(Screen.AddScreen.route) {
                        AddActivity(navController = navController)
                    }
                    composable(Screen.CardScreen.route) {
                        CardActivity(navController = navController)
                    }
                    composable(Screen.ContactScreen.route) {
                        ContactActivity(navController =  navController)
                    }
                    composable(Screen.LoginScreen.route) {
                        LoginActivity(navController = navController)
                    }
                    composable(Screen.RegisterScreen.route) {
                        RegisterActivity(navController = navController)
                    }
                    composable(Screen.SplashScreen.route) {
                        SplashActivity(navController =  navController)
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        WebAgendaTheme {
            //CardActivity(navController = {}, viewModel = viewModel())
        }
    }
}