package com.xenatronics.webagenda.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.components.ExpandableContactCard
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.PostID
import com.xenatronics.webagenda.data.ResponseContact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.Constants.SHRINK_DELAY
import com.xenatronics.webagenda.viewmodel.ViewModelContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ListContactScreen(navController: NavController, viewModel: ViewModelContact = hiltViewModel()) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val contact =
                        Gson().toJson(ResponseContact()) // on convertit la classe en chaine String
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
            ListContactContent(viewModel = viewModel,
                navController = navController,
                onSwipToDelete = { action, contact ->
                    if (action == Action.DELETE) {

                    }
                }
            )
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun ListContactContent(
    navController: NavController,
    viewModel: ViewModelContact,
    onSwipToDelete: (Action, ResponseContact) -> Unit
) {
    val contacts = viewModel.allContactFlow.collectAsState()
   // val contactList = remember { mutableListOf<Contact>() }

    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color(
            ContextCompat.getColor(
                LocalContext.current,
                R.color.white
            )
        )
    ) {
        val selectedItem = mutableStateOf(ResponseContact())
        LazyColumn(Modifier.fillMaxSize()) {
            items(contacts.value) { item ->
                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            onSwipToDelete(Action.DELETE, item)
                        }
                        true
                    }
                )
                val dismissDirection = state.dismissDirection
                val isDismissed = state.isDismissed(DismissDirection.EndToStart)
                var itemAppeared by remember { mutableStateOf(false) }
                val degrees by animateFloatAsState(
                    targetValue = if (state.targetValue == DismissValue.Default) 0f else -45f
                )
                if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                    val scope = rememberCoroutineScope()
                    scope.launch(Dispatchers.Main) {
                        delay(300) // pour jouer l'animation pour l'effacement aussi
                        withContext(Dispatchers.IO) {
                            viewModel.DeleteContact(PostID(item.id))
                        }
                    }
                }
                LaunchedEffect(key1 = true) {
                    itemAppeared = true
                }
                AnimatedVisibility(
                    visible = itemAppeared && !isDismissed,
                    enter = expandVertically(
                        animationSpec = tween(durationMillis = SHRINK_DELAY)
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(durationMillis = SHRINK_DELAY)
                    )
                ) {
                    SwipeToDismiss(
                        state = state,
                        dismissThresholds = { FractionalThreshold(0.2f) },
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color = when (state.targetValue) {
                                DismissValue.DismissedToStart -> MaterialTheme.colors.primary
                                DismissValue.DismissedToEnd -> Color.Transparent
                                DismissValue.Default -> Color.Transparent
                            }
                            RedBackground(degrees = degrees, color)
                        },
                        dismissContent = {
                            ExpandableContactCard(
                                selected = selectedItem.value == item,
                                contact = item,
                                onCardArrowClick = { viewModel.onCardArrowClicked(item.id) },
                                expanded = expandedCardIds.value.contains(item.id),
                                onClickItem = {
                                    selectedItem.value = it
                                },
                                navController = navController
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun TestRow(contact: ResponseContact) {
    Row(
        modifier = Modifier
            .height(45.dp)
            .fillMaxSize()
            .background(color = Color.Yellow)

    ) {
        Text(text = contact.nom)
        Text(text = contact.tel)
    }
}

@Composable
fun RedBackground(degrees: Float, color: Color) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier
                .rotate(degrees = degrees)
                .padding(end = 8.dp),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_item),
            tint = Color.White
        )
    }
}
