package com.xenatronics.webagenda.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.presentation.screens.LoginScreen
import com.xenatronics.webagenda.presentation.screens.RegisterScreen
import com.xenatronics.webagenda.presentation.screens.SplashScreen
import com.xenatronics.webagenda.presentation.screens.listcontact.ListContactScreen
import com.xenatronics.webagenda.presentation.screens.listrdv.ListRdvScreen
import com.xenatronics.webagenda.presentation.screens.login.ViewModelLogin
import com.xenatronics.webagenda.presentation.screens.new_contact.NewContactScreen
import com.xenatronics.webagenda.presentation.screens.new_contact.NewContactViewModel
import com.xenatronics.webagenda.presentation.screens.new_rdv.NewRdvScreen
import com.xenatronics.webagenda.presentation.screens.new_rdv.NewRdvViewModel
import com.xenatronics.webagenda.presentation.ui.theme.WebAgendaTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebAgendaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.ListRdvScreen.route
                ) {
                    addSplash(navController = navController)
                    addLogin(navController = navController)
                    addRegister(navController = navController)
                    addListRdv(navController = navController)
                    addListContact(navController = navController)
                    addNewContact(navController = navController)
                    addNewRdv(navController = navController)
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
fun NavGraphBuilder.addLogin(navController: NavController) {
    composable(Screen.LoginScreen.route) {
        val viewModel: ViewModelLogin = hiltViewModel()
        val keyboardController = LocalSoftwareKeyboardController.current
        LoginScreen(
            navController = navController,
            viewModel = viewModel,
            onEvent = { event ->
                keyboardController?.hide()
                viewModel.onEvent(event)
            }
        )
    }
}

@ExperimentalComposeUiApi
fun NavGraphBuilder.addRegister(navController: NavController) {
    composable(Screen.RegisterScreen.route) {
        RegisterScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

fun NavGraphBuilder.addSplash(navController: NavController) {
    composable(Screen.SplashScreen.route) {
        SplashScreen(navController = navController)
    }
}

fun NavGraphBuilder.addListRdv(navController: NavController) {
    composable(Screen.ListRdvScreen.route) {
        ListRdvScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun NavGraphBuilder.addListContact(navController: NavController) {
    composable(Screen.ListContactScreen.route) {
        ListContactScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.addNewRdv(navController: NavController) {
    composable(
        route = Screen.NewRdvScreen.route + "/{rdv}",
        arguments = listOf(navArgument("rdv") { type = NavType.StringType })
    ) { backStackEntry ->
        backStackEntry.arguments?.getString("rdv")?.let {
            val rdv = Gson().fromJson(it, Rdv::class.java)
            val viewModel: NewRdvViewModel = hiltViewModel()
            viewModel.setSelectRdv(rdv)
            NewRdvScreen(
                navController = navController,
                viewModel = viewModel,
                rdv = rdv,
            )
        }
    }
}

@ExperimentalComposeUiApi
fun NavGraphBuilder.addNewContact(navController: NavController) {
    composable(
        route = Screen.NewContactScreen.route + "/{contact}",
        arguments = listOf(navArgument("contact") { type = NavType.StringType })
    ) { backStackEntry ->
        backStackEntry.arguments?.getString("contact")?.let {
            //on convertit la chaine en objet Contact
            val contact = Gson().fromJson(it, Contact::class.java)
            val viewModel: NewContactViewModel = hiltViewModel()
            viewModel.setSelectContact(contact = contact)

            NewContactScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}
