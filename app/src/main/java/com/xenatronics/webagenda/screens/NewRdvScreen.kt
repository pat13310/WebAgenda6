package com.xenatronics.webagenda.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UIComboContact
import com.xenatronics.webagenda.components.UiDatePicker
import com.xenatronics.webagenda.components.UiTimePicker
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.getDateFormatter
import com.xenatronics.webagenda.util.getTimeFormatter
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@Composable
fun NewRdvScreen(
    navController: NavController,
    viewModel: ViewModelRdv,
    rdv: Rdv
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                NewTaskBar(if (rdv.id == 0) "Nouveau rendez-vous" else "Modifier rendez-vous",
                    NavigateToListScreen = {
                        if (rdv.id==0) {
                            viewModel.updateFields()
                            viewModel.handleRdvAction(Action.ADD)
                            navController.navigate(Screen.ListRdvScreen.route)
                        }
                        else{
                            viewModel.handleRdvAction(Action.UPDATE)
                            navController.navigate(Screen.ListRdvScreen.route)
                        }
                    })
            },
            content = {
                NewRdvContent(
                    navController = navController,
                    viewModel = viewModel,
                    rdv = rdv
                )
            }
        )
    }
}

@Composable
fun NewRdvContent(
    navController: NavController,
    viewModel: ViewModelRdv,
    rdv: Rdv,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LaunchedEffect(key1 = true) {
            viewModel.loadContact()
        }
        val listContact = viewModel.allContactFlow.collectAsState()
        val timestamp=rdv.date
        Spacer(modifier = Modifier.height(12.dp))
        UIComboContact(
            options = listContact.value.toList().sortedBy { contact ->  contact.nom },
            viewModel = viewModel,
            text = rdv.nom,
            onText = {
                rdv.nom = it
                viewModel.nom.value=it
            },
            onNavigate = { route ->
                navController.navigate(route = route)
            }
        )
        UiDatePicker(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            rdv=rdv,
            text= getDateFormatter(timestamp)
        )
        UiTimePicker(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            viewModel = viewModel,
            rdv=rdv,
            text= getTimeFormatter(timestamp)
        )
    }
}
