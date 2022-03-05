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
fun DateActivity(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        Scaffold(
            topBar = {
                NewTaskBar(NavigateToListScreen = {})
            },
            content = {
                DateContent()
            }
        )
    }
}

@Composable
fun DateContent() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        UiDatePicker(texte ="Aujourd'hui" , updateDialogDate ={} )
        //UiTimePicker(activate = true, texte ="08:23" , updateTime ={} )
    }
}
