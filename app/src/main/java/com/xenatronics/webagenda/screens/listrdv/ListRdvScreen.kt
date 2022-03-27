package com.xenatronics.webagenda.screens.listrdv

import android.content.pm.ActivityInfo
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.screens.listrdv.ListRdvContent
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.LockScreenOrientation
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@Composable
fun ListRdvScreen(
    navController: NavController,
    viewModel: ViewModelRdv
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // on convertit la classe en chaine String
                    val rdv = Gson().toJson(Rdv())
                    navController.navigate(Screen.NewRdvScreen.route + "/$rdv")
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add contact"
                )
            }
        },
        topBar = {
            ListTaskBar("Liste", NavigateToListScreen = {action->
                if (action == Action.DELETE){

                }

            })
        },
        content = {
            ListRdvContent(
                navController = navController,
                viewModel = viewModel
            )
        }
    )
}

