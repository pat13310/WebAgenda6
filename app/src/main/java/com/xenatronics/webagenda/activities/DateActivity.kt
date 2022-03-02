package com.xenatronics.webagenda.activities

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.xenatronics.webagenda.components.NewTaskBar

@Composable
fun DateActivity() {
    Scaffold(
        topBar = {
            NewTaskBar(NavigateToListScreen = {})
        },
        content = {
            DateContent()
        }
    )
}

@Composable
fun DateContent() {

}
