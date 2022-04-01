package com.xenatronics.webagenda.screens

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
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
                RegisterContent2(
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
fun RegisterContent2(
    modifier: Modifier,
    viewModel: ViewModelRegister,
    navController: NavController
) {
    var nom by viewModel.nom
    var mail by viewModel.mail
    var password by viewModel.password

    BoxWithConstraints {
        val constraint = decoupledConstraints(16.dp)
        ConstraintLayout(
            constraint
        ) {
            Image(
                painter = painterResource(id = R.drawable.register),
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .layoutId("image")
            )
            UITextStandard(
                modifier = Modifier
                    .layoutId("textNom")
                    .fillMaxWidth(0.92f),
                label = "Nom de famille",
                icon = Icons.Default.Person,
                value = nom,
                onTextChanged = {
                    nom = it
                })
            UITextStandard(
                modifier = Modifier
                    .layoutId("textMail")
                    .fillMaxWidth(0.92f),
                label = "Adresse mail",
                icon = Icons.Default.Person,
                value = mail,
                onTextChanged = {
                    mail = it
                })
            UITextPassword(
                modifier = Modifier
                    .layoutId("textPassword")
                    .fillMaxWidth(0.92f),
                value = password,
                onTextChanged = {
                    password = it
                }
            )
            AnnotatedRegisterClickableText(
                modifier = Modifier.layoutId("textLink"),
                onLink = {
                    navController.navigate(Screen.LoginScreen.route)
                })
        }
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
                    // Log.d("Clicked URL", it.item)
                }
        }
    )
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val image = createRefFor("image")
        val textMail = createRefFor("textMail")
        val textNom = createRefFor("textNom")
        val textPassword = createRefFor("textPassword")
        val textLink = createRefFor("textLink")
        constrain(image) {
            top.linkTo(parent.top, 0.dp)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
        constrain(textNom) {
            top.linkTo(image.bottom, margin = 0.dp)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
        constrain(textMail) {
            top.linkTo(textNom.bottom, margin = margin)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
        constrain(textPassword) {
            top.linkTo(textMail.bottom, margin = margin)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
        constrain(textLink) {
            top.linkTo(textPassword.bottom, margin = 20.dp)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
    }
}
