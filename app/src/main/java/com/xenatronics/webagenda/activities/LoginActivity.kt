package com.xenatronics.webagenda.activities

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.xenatronics.webagenda.Action
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.navigation.Screen

@Composable
fun LoginActivity(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                NewTaskBar(
                    "Connexion",
                    NavigateToListScreen = { action ->
                        if (action == Action.ADD) {
                            navController.navigate(Screen.AddScreen.route)
                        }
                    },
                    noBack = true
                )
            },
            content = {
                LoginContent(modifier = Modifier, navController)
            }
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier,
    navController: NavController
) {

}
