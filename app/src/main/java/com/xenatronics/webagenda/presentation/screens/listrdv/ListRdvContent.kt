package com.xenatronics.webagenda.presentation.screens.listrdv

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.xenatronics.webagenda.presentation.components.ExtraCardRdv

@Composable
fun ListRdvContent(
    onNavigate:(String)->Unit,
    viewModel: ViewModelRdv
) {
    viewModel.loadRdv()
    viewModel.loadContact()
    val cards = viewModel.allRdvFlow.collectAsState()
    var selectedRdv by viewModel.selectRdv

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
    ) {
        items(cards.value.toMutableList()) {item->
            var expanded by remember { mutableStateOf(false) }
            ExtraCardRdv(
                card = item,
                contact = viewModel.getContact(item.id_contact),
                onCardArrowClick = {
                    selectedRdv = item
                    expanded=!expanded
                },
                expanded = expanded,
                selected = item == selectedRdv,
                onSelectItem = {rdv-> viewModel.setSelect(rdv) },
                onNavigate=onNavigate,
            )
        }
    }
}
