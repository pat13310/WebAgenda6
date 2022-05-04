package com.xenatronics.webagenda.presentation.screens

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.LockScreenOrientation
import com.xenatronics.webagenda.presentation.ui.theme.dark_gray
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    //var startAnimation by remember { mutableStateOf(false) }
    /*val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = DELAY_SPLASH),
    )*/
    SplashContent()
    LaunchedEffect(key1 = true) {
        //startAnimation = true
        delay(1500)
        navController.popBackStack()
        navController.navigate(Screen.LoginScreen.route)
    }
}

@Composable
fun SplashContent() {
    Box(
        Modifier
            .fillMaxSize()
            .background(dark_gray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            //ha = alpha),
            painterResource(id = R.drawable.logo),
            contentDescription = ""
        )

    }
}