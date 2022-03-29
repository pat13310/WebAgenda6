package com.xenatronics.webagenda.screens.listrdv

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.components.UIAlertDialog
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.LockScreenOrientation
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ListRdvScreen(
    navController: NavController,
    viewModel: ViewModelRdv
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val scaffoldState = rememberScaffoldState()
    val isOpen = remember { mutableStateOf(false) }
    val selectedItem by viewModel.selectRdv
    val action = viewModel.action

    if (isOpen.value) {
        UIAlertDialog(
            title = "Suppression",
            message = "Voulez-vous supprimer le rendez-vous avec ${selectedItem.nom}?",
            onValidate = {
                isOpen.value = false
                action.value = Action.DELETE
            },
            onCancel = {
                isOpen.value = false
                action.value = Action.NO_ACTION
            },
        )
    }
    viewModel.handleRdvAction(action.value)
    action.value = Action.NO_ACTION
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // on convertit l'objet rdv en chaine String
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
            ListTaskBar("Vos rendez-vous",
                closeAction = false,
                valideAction = false,
                deleteAction = viewModel.allRdvFlow.value.isNotEmpty(),
                NavigateToListScreen = { action ->
                    if (action == Action.DELETE) {
                        if (selectedItem.id > 0)
                            isOpen.value = true
                    }

                })
        },
        content = {
            ListRdvContent(
                navController = navController,
                viewModel = viewModel,
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
    )
}


