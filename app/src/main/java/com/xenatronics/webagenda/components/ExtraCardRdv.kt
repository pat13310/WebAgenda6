package com.xenatronics.webagenda.components

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Constants

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExtraCardRdv(
    card: Rdv,
    contact: Contact?,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    selected: Boolean,
    onSelectItem: (Rdv) -> Unit,
    onNavigate:(String)->Unit
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
    val cardHeight by transition.animateDp({
        tween(
            durationMillis = Constants.EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (expanded) 168.dp else 68.dp
    }

    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        border = BorderStroke(
            width = 1.dp,
            if (!selected) Color.LightGray else Color.Black
        ),
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(cardPaddingHorizontal, 16.dp, end = cardPaddingHorizontal, bottom = 0.dp)
            .clickable { onSelectItem(card) }
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardArrow(
                    Modifier.weight(1f),
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardRdvTitle(
                    selected = selected,
                    modifier = Modifier.weight(6f),
                    title = card.nom,
                    date = card.date
                )
            }
        }
        if (contact != null) {
            ExpandableRdvContent(
                contact = contact,
                onNavigate=onNavigate,
                expanded=expanded,
            )
        }
    }
}



@Composable
fun ExpandableRdvContent(
    contact: Contact,
    onNavigate: (String) -> Unit,
    expanded: Boolean,
) {
    Column(modifier = Modifier.padding(start = 16.dp,top= 38.dp, end=8.dp, bottom = 6.dp)) {
        Text(
            text = contact.adresse,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = contact.cp + " " + contact.ville,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = contact.tel,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            Modifier
                .fillMaxWidth()){
            Text(
                modifier= Modifier.weight(6f),
                text = contact.mail,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            if (expanded) {
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.NewRdvScreen.route) }) {
                    Icon(Icons.Filled.Edit, contentDescription = null)
                }
            }
        }
    }
}


@Composable
fun CardRdvTitle(
    selected: Boolean,
    modifier: Modifier,
    title: String,
    date: Long
) {
    Text(
        modifier = modifier,
        text = title,
        fontSize = 15.sp,
        fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold,
        textAlign = TextAlign.Left
    )
    Text(
        modifier = if (selected) Modifier.alpha(1f) else Modifier.alpha(0.90f),
        text = convertTime(date),
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        fontSize = 12.sp,
        textAlign = TextAlign.Right
    )
}

