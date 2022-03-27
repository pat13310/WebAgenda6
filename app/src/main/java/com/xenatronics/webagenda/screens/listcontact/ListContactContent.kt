package com.xenatronics.webagenda.screens.listcontact

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.components.ExpandableContactCard2
import com.xenatronics.webagenda.components.SwipeBackground
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.ui.theme.medium_gray
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.viewmodel.ViewModelContact
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun HandleContactContent(
    viewModel: ViewModelContact,
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    val action = viewModel.action

//    LaunchedEffect(key1 = action) {
//        viewModel.load()
//    }
    viewModel.load()
    val contacts by viewModel.allContactFlow.collectAsState()

    if (contacts.isEmpty()) {
        ListContactEmptyContent()
    } else {
        ShowSnackBar(
            scaffoldState = scaffoldState,
            action = action.value,
            onUndoClick = {
                if (it == Action.UNDO) {
                    action.value = it
                }
            },
            title = viewModel.nom.value,)
            //onComplete = {})

        viewModel.handleContactAction(action = action.value)
        action.value = Action.NO_ACTION

        ListContactContent(
            contacts = contacts.toMutableList(),
            viewModel = viewModel,
            navController = navController,
            onSwipToDelete = { act, contact ->
                if (act == Action.DELETE) {
                    action.value = act
                    //on supprime une eventuelle fenetre avant
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    viewModel.updateFields(contact = contact)
                }
            },
            onSelectItem = {contact->
                viewModel.updateFields(contact = contact)
            }
        )
    }
}


@Composable
fun ListContactEmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(
                id = R.drawable.ic_unhappy
            ),
            contentDescription = stringResource(id = R.string.unhappy),
            tint = medium_gray
        )
        Text(
            text = stringResource(id = R.string.no_found),
            color = medium_gray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi

@Composable
fun ListContactContent(
    contacts: MutableList<Contact>,
    navController: NavController,
    viewModel: ViewModelContact,
    onSwipToDelete: (Action, Contact) -> Unit,
    onSelectItem:(Contact)->Unit,
) {
    var selectedItem by viewModel.selectedItem

    LazyColumn(Modifier.fillMaxSize()) {
        items(contacts, key={it.id}){item->
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        selectedItem = item
                        contacts.remove(item)
                        onSwipToDelete(Action.DELETE, item)
                    }
                    true
                }
            )
            val dismissDirection = state.dismissDirection
            val isDismissed = state.isDismissed(DismissDirection.EndToStart)
            val degrees by animateFloatAsState(
                targetValue = if (state.targetValue == DismissValue.Default) 0f else -45f
            )
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(250L)
                }
            }
            SwipeToDismiss(
                modifier=Modifier.animateItemPlacement(),
                state = state,
                dismissThresholds = { FractionalThreshold(0.33f) },
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val color = when (state.targetValue) {
                        DismissValue.DismissedToStart -> {
                            MaterialTheme.colors.secondaryVariant
                        }
                        DismissValue.DismissedToEnd -> Color.Transparent
                        DismissValue.Default -> MaterialTheme.colors.primary
                    }
                    if (state.dismissDirection == DismissDirection.EndToStart) {
                        selectedItem = item
                        //viewModel.updateFields(item)
                    }
                    SwipeBackground(degrees = degrees, color)
                },
                dismissContent = {
                    ExpandableContactCard2(
                        selected = selectedItem == item,
                        contact = item,
                        onCardArrowClick = { selectedItem = item },
                        onSelectItem = {
                            selectedItem = item
                            onSelectItem(item)
                        },
                        onNavigate = { route ->
                            navController.navigate(route)
                        },
                    )
                }
            )
        }
    }
}

