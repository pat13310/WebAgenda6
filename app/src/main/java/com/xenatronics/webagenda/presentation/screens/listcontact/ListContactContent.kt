package com.xenatronics.webagenda.presentation.screens.listcontact

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.xenatronics.webagenda.common.events.ListContactEvent
import com.xenatronics.webagenda.presentation.components.ExpandableContactCard
import com.xenatronics.webagenda.presentation.components.SwipeBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalFoundationApi
@ExperimentalMaterialApi

@Composable
fun ListContactContent(
    navController: NavController,
    viewModel: ListContactViewModel,
) {
    val contacts = viewModel.allContactFlow.collectAsState()
    var selectedItem by viewModel.selectedContact

    viewModel.loadContact()

    LazyColumn(Modifier.fillMaxSize()) {
        items(contacts.value.toMutableList()) { item ->
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        viewModel.onEvent(ListContactEvent.OnQueryDelete)
                        viewModel.selectedContact.value=item
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
                modifier = Modifier.animateItemPlacement(),
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
                    }
                    SwipeBackground(degrees = degrees, color)
                },
                dismissContent = {
                    ExpandableContactCard(
                        selected = selectedItem == item,
                        contact = item,
                        onCardArrowClick = { selectedItem = item },
                        onSelectItem = {
                            selectedItem = item
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
