package com.xenatronics.webagenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xenatronics.webagenda.activities.CardMain
import com.xenatronics.webagenda.activities.CardsScreen
import com.xenatronics.webagenda.activities.DateActivity
import com.xenatronics.webagenda.activities.NewMain
import com.xenatronics.webagenda.ui.theme.WebAgendaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WebAgendaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "date"
                ) {
                    composable("date") {
                        DateActivity(navController = navController)
                    }
                    composable("contact") {
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        WebAgendaTheme {
            CardMain(viewModel = viewModel())
        }
    }
}