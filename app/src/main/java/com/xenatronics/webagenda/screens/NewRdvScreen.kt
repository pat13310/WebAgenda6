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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UI2ComboContact
import com.xenatronics.webagenda.components.UiDatePicker
import com.xenatronics.webagenda.components.UiTimePicker
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@Composable
fun NewRdvScreen(
    navController: NavController,
    viewModel: ViewModelRdv
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
                            navController.navigate(Screen.NewContactScreen.route)
                        }
                    })
            },
            content = {
                DateContent(
                    modifier = Modifier,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        )
    }
}

@Composable
fun DateContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: ViewModelRdv
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //var selectedOptionText by remember { mutableStateOf("") }
        val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
        Spacer(modifier = Modifier.height(12.dp))

        UI2ComboContact(navController = navController, options = options, viewModel = viewModel)
        UiDatePicker(viewModel = viewModel)
        UiTimePicker(viewModel = viewModel)
    }
}
