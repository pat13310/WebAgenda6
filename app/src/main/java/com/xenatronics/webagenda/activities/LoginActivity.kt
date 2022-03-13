package com.xenatronics.webagenda.activities

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.xenatronics.webagenda.Action
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UITextPassword
import com.xenatronics.webagenda.components.UITextStandard
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.viewmodel.ViewModelLogin
import dagger.hilt.android.lifecycle.HiltViewModel

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
                    modifier = Modifier.fillMaxSize(),
                    viewModel =  hiltViewModel(), navController = navController
                )
            }
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier,
    viewModel: ViewModelLogin,
    navController: NavController,
) {
    var nom by viewModel.nom
    var password by viewModel.password

    Box(
        modifier = Modifier
            .padding(
                top = 120.dp,
                start = 0.dp,
                end = 0.dp,
                bottom = 50.dp,
            ),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            UITextStandard(
                label = "Login",
                icon = Icons.Default.Person,
                value = nom,
                onTextChanged = {
                    nom = it
                })
            Spacer(modifier = Modifier.height(18.dp))
            UITextPassword(
                value = password,
                onTextChanged = {
                    password = it
                }
            )
        }
        AnnotatedClickableText(
            modifier = Modifier.align(Alignment.BottomCenter),
            onLink = {
                navController.navigate(Screen.RegisterScreen.route)
            }
        )
    }
}

@Composable
fun AnnotatedClickableText(modifier: Modifier,
                           onLink:()->Unit
) {
    val annotatedText = buildAnnotatedString() {
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.Light
            )
        ) {
            append(stringResource(id = R.string.Register))
        }
        addStringAnnotation(
            tag = "ACTION",
            annotation = stringResource(id = R.string.SignUp), start = 20, end = 35
        )
        append("  ")
        withStyle(
                style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.W800
                            )
        ) {
            append(stringResource(id = R.string.SignUp))
        }
    }
    ClickableText(
        modifier = modifier,
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "ACTION", start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    onLink()
                    Log.d("Clicked URL", it.item)
                }
        }
    )
}
