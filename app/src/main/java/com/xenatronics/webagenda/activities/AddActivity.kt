package com.xenatronics.webagenda.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UiDatePicker
import com.xenatronics.webagenda.components.UiTimePicker

@Composable
fun AddActivity(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                NewTaskBar(NavigateToListScreen = {})
            },
            content = {
                DateContent(modifier = Modifier)
            }
        )
    }
}

@Composable
fun DateContent(modifier: Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.White)) {
        UiDatePicker(texte ="Aujourd'hui" , updateDialogDate ={} )
        UiTimePicker(texte ="08:23"  )
    }
}
