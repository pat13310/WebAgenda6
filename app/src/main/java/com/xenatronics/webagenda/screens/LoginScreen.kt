package com.xenatronics.webagenda.screens


import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.xenatronics.webagenda.data.User
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.repository.RepositoryLogin
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.util.LockScreenOrientation
import com.xenatronics.webagenda.util.StatusLogin
import com.xenatronics.webagenda.viewmodel.ViewModelLogin

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: ViewModelLogin
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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
                            val name by viewModel.nom
                            val password by viewModel.password
                            val status = viewModel.stateLogin.value
                            viewModel.login(User(name = name, password = password))
                            if (status==StatusLogin.Ok){
                                Log.d("Login","OK")
                                navController.navigate(Screen.ListRdvScreen.route)
                            }
                        }
                    },
                    noBack = true
                )
            },
            content = {
                LoginContent2(
                    modifier = Modifier
                        .fillMaxSize(),
                    viewModel = viewModel, navController = navController
                )
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginContent2(
    modifier: Modifier,
    viewModel: ViewModelLogin,
    navController: NavController,
) {
    var nom by viewModel.nom
    var password by viewModel.password

    BoxWithConstraints{
        val constraint = decoupledConstraints(16.dp)
        ConstraintLayout(constraint) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = null,
                modifier= Modifier
                    .fillMaxWidth()
                    .layoutId("image")
            )
            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textLogin"),
                label = "Login",
                icon = Icons.Default.Person,
                value = nom,
                onTextChanged = {
                    nom = it
                }
            )
            UITextPassword(
                modifier =
                Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textPassword"),
                value = password,
                onTextChanged = {
                    password = it
                }
            )
            AnnotatedClickableText(
                modifier = Modifier.layoutId("textLink"),
                onLink = {
                    navController.navigate(Screen.RegisterScreen.route)
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
