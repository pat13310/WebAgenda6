package com.xenatronics.webagenda.activities

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.viewmodel.ViewModelRdv

@Composable
fun ListContactActivity(navController:NavController){
    Scaffold(
        topBar = {
            ListTaskBar("Vos contacts",NavigateToListScreen = {
                navController.popBackStack() })
        },
        content = {
            ListContactContent(navController = navController,
                viewModel = viewModel())
        }
    )
}

@Composable
fun ListContactContent(navController: NavController, viewModel: ViewModelRdv) {

}
