package com.xenatronics.webagenda.screens.listcontact

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.viewmodel.ViewModelContact
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListContactScreen(navController: NavController, viewModel: ViewModelContact = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    var action by viewModel.action

    val contacts = viewModel.allContactFlow.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.load()
    }

    viewModel.handleContactAction(action = action)
    ShowSnackBar(
        scaffoldState = scaffoldState,
        action = action,
        onUndoClick = {
            action = it
        },
        title = viewModel.nom.value
    )
    action = Action.NO_ACTION

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
            ListTaskBar("Vos contacts", NavigateToListScreen = {
                navController.popBackStack()
            })
        },
        content = {
            HandleContactContent(
                contacts = contacts.value,
                viewModel = viewModel,
                navController = navController
            )
        }
    )
}

@Composable
fun ShowSnackBar(
    action: Action,
    scaffoldState: ScaffoldState,
    onUndoClick: (Action) -> Unit,
    title: String = "",
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action == Action.DELETE) {
            scope.launch {
                val snackResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = translateMessage(action, title),
                    actionLabel = setActionLabel(action = action)
                )
                if (snackResult == SnackbarResult.ActionPerformed && action == Action.DELETE)
                    onUndoClick(Action.UNDO)
            }
        }
    }
}


fun setActionLabel(action: Action): String {
    return when (action) {
        Action.DELETE -> {
            "Rétablir"
        }
        Action.UNDO -> {
            ""
        }
        else -> {
            "Ok"
        }
    }
}

fun translateMessage(
    action: Action,
    title: String
): String {

    return when (action) {
        Action.DELETE -> "Suppression de $title"
        else -> ""
    }
}
