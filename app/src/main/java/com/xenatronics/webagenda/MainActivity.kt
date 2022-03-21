package com.xenatronics.webagenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.screens.*
import com.xenatronics.webagenda.screens.listcontact.ListContactScreen
import com.xenatronics.webagenda.ui.theme.WebAgendaTheme
import com.xenatronics.webagenda.viewmodel.ViewModelContact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebAgendaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.ListContactScreen.route
                ) {
                    composable(Screen.NewRdvScreen.route) {
                        NewRdvScreen(
                            navController = navController,
                            viewModel = hiltViewModel()
                        )
                    }
                    composable(Screen.ListRdvScreen.route) {
                        ListRdvScreen(
                            navController = navController,
                            viewModel = hiltViewModel()
                        )
                    }
                    composable(
                        route = Screen.NewContactScreen.route + "/{contact}",
                        arguments = listOf(navArgument("contact") { type = NavType.StringType })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("contact")?.let() {
                            //on convertit la chaine en objet Contact
                            val contact = Gson().fromJson(it, Contact::class.java)
                            NewContactScreen(
                                navController = navController,
                                contact = contact
                            )
                        }
                    }
                    composable(Screen.ListContactScreen.route) {
                        val viewModel:ViewModelContact= hiltViewModel()
                        //viewModel.load()
                        ListContactScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.LoginScreen.route) {
                        LoginScreen(
                            navController = navController,
                            viewModel = hiltViewModel()
                        )
                    }
                    composable(Screen.RegisterScreen.route) {
                        RegisterScreen(
                            navController = navController,
                            viewModel = hiltViewModel()
                        )
                    }
                    composable(Screen.SplashScreen.route) {
                        SplashScreen(navController = navController)
                    }
                }
            }
        }
    }


}