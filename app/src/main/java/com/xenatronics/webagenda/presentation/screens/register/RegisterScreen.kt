package com.xenatronics.webagenda.presentation.screens.register

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
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.common.events.RegisterEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.common.util.LockScreenOrientation
import com.xenatronics.webagenda.common.util.getMessage
import com.xenatronics.webagenda.presentation.components.NewTaskBar
import com.xenatronics.webagenda.presentation.components.UITextStandard
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: ViewModelRegister
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focus = remember { mutableStateOf("") }

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowErrorMessage -> {
                    keyboardController?.hide()
                    focus.value = event.focus
                    scaffoldState.snackbarHostState.showSnackbar(
                        actionLabel = "",
                        message = getMessage(context, event.resultUseCase)
                    )
                }
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
                    "Inscription",
                    NavigateToListScreen = { action ->
                        if (action == Action.ADD) {
                            viewModel.onEvent(RegisterEvent.OnSubmit)
                        }
                    },
                    noBack = true
                )
            },
            content = {
                RegisterContent(
                    viewModel = viewModel,
                    focus = focus.value
                )
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun RegisterContent(
    viewModel: ViewModelRegister,
    focus: String
) {
    val state = viewModel.state

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
                focus = (focus == "name"),
                modifier = Modifier
                    .layoutId("textNom")
                    .fillMaxWidth(0.92f),
                label = "Nom du login",
                icon = Icons.Default.Person,
                value = state.nom,
                onTextChanged = {
                    viewModel.onEvent(RegisterEvent.NameChanged(it))
                })
            UITextStandard(
                focus = (focus == "mail"),
                modifier = Modifier
                    .layoutId("textMail")
                    .fillMaxWidth(0.92f),
                label = "Adresse mail",
                icon = Icons.Default.Person,
                value = state.email,
                onTextChanged = {
                    viewModel.onEvent((RegisterEvent.EmailChanged(it)))
                })
            UITextStandard(
                keyboardType = KeyboardType.Password,
                focus = (focus == "password"),
                label = "Mot de passe",
                modifier = Modifier
                    .layoutId("textPassword")
                    .fillMaxWidth(0.92f),
                value = state.password,
                onTextChanged = {
                    viewModel.onEvent(RegisterEvent.PasswordChanged(it))
                },
                focusNext = false,
                icon = Icons.Default.VpnKey
            )
            AnnotatedRegisterClickableText(
                modifier = Modifier.layoutId("textLink"),
                onLink = {
                    viewModel.onEvent(RegisterEvent.OnNavigateLogin)
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
