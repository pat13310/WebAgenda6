package com.xenatronics.webagenda.presentation.screens.listcontact

import android.content.pm.ActivityInfo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.common.events.ListContactEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.common.util.LockScreenOrientation
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.presentation.components.ListTaskBar
import kotlinx.coroutines.flow.collect


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ListContactScreen(
    navController: NavController,
    viewModel: ListContactViewModel,
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    val result =
                        scaffoldState.snackbarHostState.showSnackbar(event.message, event.action)
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ListContactEvent.OnUndo)
                    }
                }
                is UIEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // on convertit la classe en chaine String
                    val contact = Gson().toJson(Contact())
                    navController.navigate(Screen.NewContactScreen.route + "/$contact")
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
            ListTaskBar("Vos contacts", NavigateToListScreen = { action ->
                if (viewModel.selectedContact.value.id > 0) {
                    if (action == Action.DELETE) {
                        viewModel.onEvent(ListContactEvent.OnQueryDelete)
                    }
                    if (action == Action.ADD) {
                        viewModel.onEvent((ListContactEvent.OnValidate))
                    }
                }
                if (action == Action.NO_ACTION) {
                    viewModel.onEvent(ListContactEvent.OnBack)
                }

            })
        },
        content = {
            ListContactContent(
                viewModel = viewModel,
                navController = navController,

                )
        }
    )
}

