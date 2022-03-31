package com.xenatronics.webagenda.screens

import android.content.pm.ActivityInfo
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UITextPassword
import com.xenatronics.webagenda.components.UITextStandard
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.LockScreenOrientation
import com.xenatronics.webagenda.viewmodel.ViewModelRegister

@ExperimentalComposeUiApi
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: ViewModelRegister
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                NewTaskBar(
                    "Inscription",
                    NavigateToListScreen = { action ->
                        if (action == Action.ADD) {
                            navController.navigate(Screen.NewRdvScreen.route)
                        }
                    },
                    noBack = true
                )
            },
            content = {
                RegisterContent(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel,
                    navController = navController
                )
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun RegisterContent(
    modifier: Modifier,
    viewModel: ViewModelRegister,
    navController: NavController
) {

    var nom by viewModel.nom
    var mail by viewModel.mail
    var password by viewModel.password

    Box(
        Modifier
            .fillMaxSize()
            .padding(
                top = 120.dp,
                start = 2.dp,
                end = 2.dp,
                bottom = 50.dp,
            )
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            UITextStandard(
                label = "nom de famille",
                icon = Icons.Default.Person,
                value = nom,
                onTextChanged = {
                    nom = it
                })
            Spacer(modifier = Modifier.height(16.dp))
            UITextStandard(
                label = "adresse mail",
                icon = Icons.Default.Person,
                value = mail,
                onTextChanged = {
                    mail = it
                })
            Spacer(modifier = Modifier.height(16.dp))
            UITextPassword(
                value = password,
                onTextChanged = {
                    password = it
                }
            )
        }
        AnnotatedRegisterClickableText(modifier = Modifier.align(Alignment.BottomCenter), onLink = {
            navController.navigate(Screen.LoginScreen.route)
        })
    }
}


@Composable
fun AnnotatedRegisterClickableText(
    modifier: Modifier,
    onLink: () -> Unit
) {
    val annotatedText = buildAnnotatedString() {
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.Light
            )
        ) {
            append(stringResource(id = R.string.Login))
        }
        addStringAnnotation(
            tag = "ACTION",
            annotation = stringResource(id = R.string.SignUp), start = 15, end = 35
        )
        append("  ")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.W800
            )
        ) {
            append(stringResource(id = R.string.SignIn))
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
