package com.xenatronics.webagenda.screens.listcontact

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.ui.theme.medium_gray
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.Constants
import com.xenatronics.webagenda.viewmodel.ViewModelContact
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun HandleContactContent(
    contacts: List<Contact>,
    viewModel: ViewModelContact,
    navController: NavController,
    onSwipToDelete: (Action, Contact) -> Unit,
    onSelectItem: (Contact) -> Unit,
) {
    if (contacts.isEmpty()) {
        ListContactEmptyContent()
    } else {
        ListContactContent(
            contacts = contacts,
            viewModel = viewModel,
            navController = navController,
            onSwipToDelete = onSwipToDelete,
            onSelectItem = onSelectItem
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


@ExperimentalMaterialApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ListContactContent(
    contacts: List<Contact>,
    navController: NavController,
    viewModel: ViewModelContact,
    onSwipToDelete: (Action, Contact) -> Unit,
    onSelectItem:(Contact)->Unit,
) {
    var selectedItem by viewModel.selectedItem

    LazyColumn(Modifier.fillMaxSize()) {
        items(contacts) { item ->
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
            var itemAppeared by rememberSaveable { mutableStateOf(false) }
            val degrees by animateFloatAsState(
                targetValue = if (state.targetValue == DismissValue.Default) 0f else -45f
            )
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(250L)
                }
            }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }
            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = Constants.SHRINK_DELAY)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = Constants.SHRINK_DELAY)
                )
            ) {
                SwipeToDismiss(
                    state = state,
                    dismissThresholds = { FractionalThreshold(0.2f) },
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val color = when (state.targetValue) {
                            DismissValue.DismissedToStart -> {
                                MaterialTheme.colors.secondaryVariant
                            }
                            DismissValue.DismissedToEnd -> Color.Transparent
                            DismissValue.Default -> MaterialTheme.colors.primary
                        }
                        if (state.dismissDirection==DismissDirection.EndToStart) {
                            selectedItem=item
                            onSelectItem(item)
                        }
                        RedBackground(degrees = degrees, color)
                    },
                    dismissContent = {
                        ExpandableContactCard2(
                            selected = selectedItem == item,
                            contact = item,
                            onCardArrowClick = { selectedItem = item },
                            onSelectItem = { contact ->
                                onSelectItem(contact)
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
}


@Composable
fun RedBackground(degrees: Float, color: Color) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxSize()
            .background(color),
        //.clip(shape = RoundedCornerShape(8.dp)),
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
