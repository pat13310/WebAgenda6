package com.xenatronics.webagenda.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.util.Constants
import com.xenatronics.webagenda.util.Constants.FADE_IN_ANIMATION_DURATION
import com.xenatronics.webagenda.util.Constants.FADE_OUT_ANIMATION_DURATION
import java.util.*

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    card: Rdv,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val cardBgColor by transition.animateColor({

        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) Color.White else MaterialTheme.colors.primary
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 12.dp else 8.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 20.dp else 14.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = Constants.EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (expanded) 8.dp else 12.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 0f else 180f
    }

    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(cardPaddingHorizontal, 16.dp, end = cardPaddingHorizontal, bottom = 0.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardArrow(
                    Modifier.fillMaxWidth(0.1f),
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardRdvTitle(title = card.name, date = card.date)
            }
        }
        ExpandableRdvContent(
            adresse = "3 rue des mimosas",
            cp = "13310",
            ville = "St martin de crau",
            visible = expanded,
            initialVisibility = expanded
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableRdvContent(
    adresse: String = "",
    cp: String = "",
    ville: String = "",
    tel: String = "",
    mail: String = "",
    visible: Boolean = true,
    initialVisibility: Boolean = false
) {

    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }

    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterFadeIn,
        exit = exitFadeOut
    ) {
        Column(modifier = Modifier.padding(12.dp, 8.dp, 8.dp, 8.dp)) {
            Spacer(modifier = Modifier.heightIn(35.dp))
            Text(
                text = adresse,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Text(
                text = cp + " " + ville,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Text(
                text = tel,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Text(
                text = mail,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun CardRdvTitle(title: String, date: Int) {
    Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.width(100.dp))
    Text(
        text = convertTime(date.toLong()),
        fontSize = 12.sp,
        textAlign = TextAlign.Right
    )
}

