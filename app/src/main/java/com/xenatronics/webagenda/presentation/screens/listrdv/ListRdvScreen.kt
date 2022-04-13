package com.xenatronics.webagenda.presentation.screens.listrdv

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.common.events.ListRdvEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.common.util.LockScreenOrientation
import com.xenatronics.webagenda.presentation.components.TopBarWithMenuOption
import com.xenatronics.webagenda.presentation.components.UIAlertDialog
import kotlinx.coroutines.flow.collect

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ListRdvScreen(
    navController: NavController,
    viewModel: ViewModelRdv
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val scaffoldState = rememberScaffoldState()
    val showDialogDelete = remember { mutableStateOf(false) }
    val selectedItem by viewModel.selectRdv

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var action by remember { mutableStateOf(Action.NO_ACTION) }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message, event.action)
                }
                is UIEvent.ShowAlertDialog -> {
                    title = event.title
                    message = event.message
                    action = event.action
                    showDialogDelete.value = true
                }
                else -> Unit
            }
        }
    }

    if (showDialogDelete.value) {
        UIAlertDialog(
            title = title,
            message = message,
            onValidate = {
                when (action) {
                    Action.DELETE -> {
                        viewModel.OnEvent(ListRdvEvent.OnDelete)
                    }
                    Action.DELETE_ALL -> {
                        viewModel.OnEvent(ListRdvEvent.OnClean)
                    }
                    else -> Unit
                }
                showDialogDelete.value = false
            },
            onCancel = {
                showDialogDelete.value = false
                viewModel.OnEvent(ListRdvEvent.OnNothing)
            },
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.OnEvent(ListRdvEvent.OnNew)
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
            // TopBar with MenuOptions
            TopBarWithMenuOption(title = stringResource(id = R.string.rdv),
                onDelete = {
                    if (selectedItem.id > 0) {
                        viewModel.OnEvent(ListRdvEvent.OnQueryDelete)
                    }
                },
                onDeleteAll = {
                    viewModel.OnEvent(ListRdvEvent.OnQueryClean)
                },
                onLogout = {
                    viewModel.OnEvent(ListRdvEvent.OnLogout)
                }
            )
        },
        content = {
            ListRdvContent(
                viewModel = viewModel,
                onNavigate = {
                    viewModel.OnEvent(ListRdvEvent.OnEdit)
                }
            )
        }
    )
}

