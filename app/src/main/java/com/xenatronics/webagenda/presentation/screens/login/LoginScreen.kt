package com.xenatronics.webagenda.presentation.screens

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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.xenatronics.webagenda.common.events.LoginEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.util.LockScreenOrientation
import com.xenatronics.webagenda.common.util.StatusLogin
import com.xenatronics.webagenda.presentation.components.NewTaskBar
import com.xenatronics.webagenda.presentation.components.UITextPassword
import com.xenatronics.webagenda.presentation.components.UITextStandard
import com.xenatronics.webagenda.presentation.screens.login.ViewModelLogin
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: ViewModelLogin,
    onEvent: (LoginEvent) -> Unit,
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                is UIEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                NewTaskBar(
                    "Connexion",
                    NavigateToListScreen = {
                        onEvent(LoginEvent.OnSubmit)
                    },
                    noBack = true
                )
            },
            content = {
                LoginContent(
                    viewModel = viewModel,
                )
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginContent(
    viewModel: ViewModelLogin,
) {
    val state = viewModel.state
    val status by viewModel.stateLogin

    LaunchedEffect(status)
    {
        if (status == StatusLogin.Ok) {
            viewModel.onEvent(LoginEvent.onNavigateListRdv)
            //viewModel.goToRdvList(navController = navController)
        }
    }

    BoxWithConstraints {
        val constraint = decoupledConstraints(16.dp)
        ConstraintLayout(constraint) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("image")
            )
            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textLogin"),
                label = "Login",
                icon = Icons.Default.Person,
                value = state.email,//nom,
                onTextChanged = {
                    viewModel.onEvent(LoginEvent.EmailChanged(it))
                }
            )
            UITextPassword(
                modifier =
                Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textPassword"),
                value = state.password,//password,
                onTextChanged = {
                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
                }
            )
            AnnotatedClickableText(
                modifier = Modifier.layoutId("textLink"),
                onLink = {
                    viewModel.onEvent(LoginEvent.onNavigateRegister)
                }
            )
        }
    }
}


@Composable
fun AnnotatedClickableText(
    modifier: Modifier,
    onLink: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
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
            ).firstOrNull()?.let {
                onLink()
            }
        }
    )
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val image = createRefFor("image")
        val textLogin = createRefFor("textLogin")
        val textPassword = createRefFor("textPassword")
        val textLink = createRefFor("textLink")
        constrain(image) {
            top.linkTo(parent.top, 0.dp)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
        constrain(textLogin) {
            top.linkTo(image.bottom, margin = 0.dp)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
        constrain(textPassword) {
            top.linkTo(textLogin.bottom, margin = margin)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
        constrain(textLink) {
            top.linkTo(textPassword.bottom, margin = 70.dp)
            start.linkTo(parent.start, margin = margin)
            end.linkTo(parent.end, margin = margin)
        }
    }
}
