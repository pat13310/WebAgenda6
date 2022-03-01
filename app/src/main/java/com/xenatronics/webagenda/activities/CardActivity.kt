package com.xenatronics.webagenda.activities

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.xenatronics.webagenda.ListTaskBar
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.viewmodel.ViewmodelRdv
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CardMain(viewModel: ViewmodelRdv) {
    Scaffold(
        topBar = {
            ListTaskBar(NavigateToListScreen = {})
        },
        content = { CardsScreen(viewModel = viewModel) }
    )
}


@Composable
fun CardsScreen(viewModel: ViewmodelRdv) {
    val cards = viewModel.allRdvFlow.collectAsState()
    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()
    Scaffold(
        backgroundColor = Color(
            ContextCompat.getColor(
                LocalContext.current,
                R.color.white
            )
        )
    ) {
        LazyColumn {
            items(cards.value) {
                ExpandableCard(
                    card = it,
                    onCardArrowClick = { viewModel.onCardArrowClicked(it.id) },
                    expanded = expandedCardIds.value.contains(it.id),
                )
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    card: Rdv,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val EXPAND_ANIMATION_DURATION = 450


    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val cardBgColor by transition.animateColor({

        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) Color.White else MaterialTheme.colors.primary
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 12.dp else 8.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 20.dp else 14.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (expanded) 8.dp else 12.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
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
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardTitle(title = card.name, date = card.date)
            }
        }
        ExpandableContent(
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
fun ExpandableContent(
    adresse: String = "",
    cp: String = "",
    ville: String = "",
    tel: String = "",
    mail: String = "",
    visible: Boolean = true,
    initialVisibility: Boolean = false
) {
    val FADE_OUT_ANIMATION_DURATION = 100
    val FADE_IN_ANIMATION_DURATION = 200

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
fun CardTitle(title: String, date: Int) {
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

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.drop_up),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}

@SuppressLint("SimpleDateFormat")
fun convertTime(timestamp: Long): String {
    val simpleDateFormat =
        SimpleDateFormat("'Le' dd MMMM yyyy 'à' HH:mm", Locale.FRANCE)
    return simpleDateFormat.format(timestamp * 1000L)
}