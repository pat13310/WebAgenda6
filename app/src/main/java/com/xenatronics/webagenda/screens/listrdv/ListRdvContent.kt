package com.xenatronics.webagenda.screens.listrdv

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.ExtraCardRdv
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@Composable
fun ListRdvContent(navController: NavController, viewModel: ViewModelRdv) {
    viewModel.loadRdv()
    val cards = viewModel.allRdvFlow.collectAsState()
    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()
    var selectedRdv by viewModel.selectRdv

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
    ) {
        items(cards.value) {
            ExtraCardRdv(
                card = it,
                onCardArrowClick = {
                    viewModel.onCardArrowClicked(it.id)
                    selectedRdv = it
                },
                expanded = expandedCardIds.value.contains(it.id),
                selected = it == selectedRdv,
                onSelectItem = { selectedRdv = it }
            )
        }
    }
}

