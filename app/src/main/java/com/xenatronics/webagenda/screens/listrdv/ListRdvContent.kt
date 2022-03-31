package com.xenatronics.webagenda.screens.listrdv

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.ExtraCardRdv
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@Composable
fun ListRdvContent(
    onNavigate:(String)->Unit,
    viewModel: ViewModelRdv) {
    viewModel.loadRdv()
    viewModel.loadContact()
    val cards = viewModel.allRdvFlow.collectAsState()

    var selectedRdv by viewModel.selectRdv

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
    ) {
        items(cards.value.toMutableList()) {
            var expanded by remember { mutableStateOf(false) }
            ExtraCardRdv(
                card = it,
                contact = viewModel.getContact(it.id_contact),
                onCardArrowClick = {
                    selectedRdv = it
                    expanded=!expanded
                },
                expanded = expanded,
                selected = it == selectedRdv,
                onSelectItem = {rdv-> selectedRdv = rdv },
                onNavigate=onNavigate,
            )
        }
    }
}
