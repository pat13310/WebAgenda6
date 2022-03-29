package com.xenatronics.webagenda.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
                NewTaskBar("Nouveau Rendez-vous",
                    NavigateToListScreen = { action ->
                        if (action == Action.ADD) {
                            viewModel.updateFields()
                            viewModel.handleRdvAction(Action.ADD)
                            navController.navigate(Screen.ListRdvScreen.route)
                        }
                    })
            },
            content = {
                NewRdvContent(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        )
    }
}

@Composable
fun NewRdvContent(
    navController: NavController,
    viewModel: ViewModelRdv
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //var selectedOptionText by remember { mutableStateOf("") }
        LaunchedEffect(key1 = true){
            viewModel.loadContact()
        }
        val listContact=viewModel.allContactFlow.collectAsState()
        Spacer(modifier = Modifier.height(12.dp))
        UIComboContact( options = listContact.value.toList(),
            viewModel = viewModel,
            onNavigate = { route->
            navController.navigate(route = route)
        })
        UiDatePicker(viewModel = viewModel)
        UiTimePicker(viewModel = viewModel)
    }
}
