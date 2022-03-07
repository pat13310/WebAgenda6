package com.xenatronics.webagenda.activities

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.Action
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UITextPassword
import com.xenatronics.webagenda.components.UITextStandard
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.viewmodel.ViewModelLogin

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
                LoginContent(
                    modifier = Modifier,
                    viewModel = ViewModelLogin()
                )
            }
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier=Modifier.padding(16.dp),
    viewModel: ViewModelLogin
) {

    var nom by viewModel.nom
    var password by viewModel.password

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)) {
        UITextStandard(
            label="Login",
            icon= Icons.Default.Person,
            value = nom,
            onTextChanged = {
                nom=it
            })
        Spacer(modifier = Modifier.height(16.dp))
        UITextPassword(
            value = password,
            onTextChanged = {
                password=it
            }
        )
    }
}
