package com.xenatronics.webagenda.screens.listcontact

import android.content.pm.ActivityInfo
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.LockScreenOrientation
import com.xenatronics.webagenda.viewmodel.ViewModelContact
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListContactScreen(
    navController: NavController,
    viewModel: ViewModelContact,
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val scaffoldState = rememberScaffoldState()
    var action by viewModel.action
    LaunchedEffect(key1 = action){
        viewModel.load()
    }
    //on appelle cette fonction quand l'action change
    viewModel.handleContactAction(action = action)
    val contacts by viewModel.allContactFlow.collectAsState()

    ShowSnackBar(
        scaffoldState = scaffoldState,
        action = action,
        onUndoClick = { action = it },
        title = viewModel.nom.value,
        onComplete = { action = it }
    )

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
                if (viewModel.selectedItem.value.id > 0) {
                    if (action == Action.DELETE) {
                        viewModel.action.value = action
                    }
                    if (action == Action.ADD) {
                        navController.navigate(Screen.NewRdvScreen.route)
                    }
                }
            })
        },
        content = {
            HandleContactContent(
                contacts = contacts,
                viewModel = viewModel,
                navController = navController,
                onSwipToDelete = { action, contact ->
                    if (action == Action.DELETE) {
                        viewModel.updateFields(contact = contact)
                        viewModel.action.value = action
                        viewModel.selectedItem.value=contact
                        //on supprime une eventuelle fenetre avant
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    }
                },
                onSelectItem = {
                    viewModel.selectedItem.value=it
                    viewModel.updateFields(contact = it)
                }
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
    onComplete: (Action) -> Unit
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
            onComplete(Action.NO_ACTION)
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
