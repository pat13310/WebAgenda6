package com.xenatronics.webagenda.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.xenatronics.webagenda.Action
import com.xenatronics.webagenda.components.*
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.viewmodel.ViewModelAdd

@Composable
fun AddActivity(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                NewTaskBar("Nouveau Rendez-vous",
                    NavigateToListScreen = {action->
                    if (action ==  Action.ADD){
                        navController.navigate(Screen.ContactScreen.route)
                    }
                })
            },
            content = {
                DateContent(modifier = Modifier, navController, viewModel())
            }
        )
    }
}

@Composable
fun DateContent(
    modifier: Modifier,
                navController: NavController,
    viewModel: ViewModelAdd
) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.White)) {
        var selectedOptionText by remember { mutableStateOf("") }
        val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
        Spacer(modifier = Modifier.height(8.dp))

        UI2ComboContact(navController=navController,  options = options, viewModel = viewModel )
        UiDatePicker(viewModel=viewModel)
        UiTimePicker(viewModel=viewModel)
    }
}
