package com.xenatronics.webagenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xenatronics.webagenda.activities.CardActivity
import com.xenatronics.webagenda.activities.AddActivity
import com.xenatronics.webagenda.activities.CardContent
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.ui.theme.WebAgendaTheme
import com.xenatronics.webagenda.viewmodel.ViewmodelRdv

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WebAgendaTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.CardScreen.route
                ) {
                    composable(Screen.AddScreen.route) {
                        AddActivity(navController = navController)
                    }
                    composable(Screen.CardScreen.route) {
                        CardActivity(navController = navController)
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        WebAgendaTheme {
            //CardActivity(navController = {}, viewModel = viewModel())
        }
    }
}