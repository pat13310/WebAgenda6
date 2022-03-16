package com.xenatronics.webagenda.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.ListTaskBar

@Composable
fun LoadingScreen(navController: NavController, title: String = "") {
    Scaffold(
        topBar = {
            ListTaskBar(title = title, NavigateToListScreen = {
                //navController.popBackStack()
            })
        },
        content = {
            LoadingIndicator()
        }
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

