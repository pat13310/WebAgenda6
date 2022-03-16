package com.xenatronics.webagenda.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.components.ExpandableCard
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@Composable
fun ListRdvScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ListTaskBar("Liste",NavigateToListScreen = {
                navController.popBackStack() })
        },
        content = {
            ListRdvContent(navController = navController, viewModel = hiltViewModel())
        }
    )
}


@Composable
fun ListRdvContent(navController: NavController, viewModel: ViewModelRdv) {

    val cards = viewModel.allRdvFlow.collectAsState()
    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()
    Scaffold(
        backgroundColor = Color(
            ContextCompat.getColor(
                LocalContext.current,
                R.color.white
            )
        )
    ) {
        LazyColumn {
            items(cards.value) {
                ExpandableCard(
                    card = it,
                    onCardArrowClick = { viewModel?.onCardArrowClicked(it.id) },
                    expanded = expandedCardIds.value.contains(it.id),
                )
            }
        }
    }
}

