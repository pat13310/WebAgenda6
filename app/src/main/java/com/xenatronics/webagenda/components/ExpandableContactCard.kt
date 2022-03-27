package com.xenatronics.webagenda.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Constants


@SuppressLint("UnusedTransitionTargetStateParameter", "UnrememberedMutableState")
@Composable
fun ExpandableContactCard2(
    contact: Contact,
    onCardArrowClick: () -> Unit,
    onSelectItem: (Contact) -> Unit,
    onNavigate: (String) -> Unit,
    selected: Boolean,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val transitionState = remember {
        MutableTransitionState(isExpanded).apply {
            targetState = !isExpanded
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (isExpanded) Color.White else MaterialTheme.colors.secondary
    }
    val cardFgColor by transition.animateColor({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (isExpanded) Color.Black else colorResource(id = R.color.purple_900)
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (isExpanded) 12.dp else 8.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (isExpanded) 20.dp else 14.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = Constants.EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (isExpanded) 8.dp else 12.dp
    }
    val cardHeight by transition.animateDp({
        tween(
            durationMillis = Constants.EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (isExpanded) 155.dp else 68.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (isExpanded) 0f else 180f
    }
    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        border = BorderStroke(
            width = 1.dp,
            if (!selected) Color.LightGray else MaterialTheme.colors.primary
        ),
        modifier = Modifier
            .height(cardHeight)
            .fillMaxWidth()
            .padding(cardPaddingHorizontal, 8.dp, end = cardPaddingHorizontal, bottom = 8.dp)
            .clickable {
                contact.selected = selected
                onSelectItem(contact)
            },
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .padding(start = 0.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CardArrow(
                    modifier = Modifier.weight(1f),
                    degrees = arrowRotationDegree,
                    onClick = {
                        onCardArrowClick()
                        isExpanded = !isExpanded
                    }
                )
                CardContactTitle(
                    modifier = Modifier.weight(6f),
                    expanded = isExpanded,
                    contact = contact,
                    color = cardFgColor
                )
            }
        }
        ContactExtraContent(onNavigate = onNavigate,
            contact =contact ,
            onNew = {})
    }
}


@Composable
fun CardContactTitle(
    modifier: Modifier,
    expanded: Boolean,
    contact: Contact,
    color: Color,
) {
    Text(
        modifier = modifier,
        color = color,
        text = contact.nom,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left
    )
    if (!expanded) {
        Text(
            modifier = Modifier.alpha(0.75f),
            color = color,
            text = contact.tel,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Left
        )
    }
}


@Composable
fun ContactExtraContent(
    onNavigate: (String) -> Unit,
    onNew:()->Unit,
    contact: Contact,
) {
    Column(modifier = Modifier.padding(start=16.dp,top= 42.dp, end =  8.dp, bottom =  10.dp),

    ) {
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
                .fillMaxWidth()
                .align(CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.88f),
                text = contact.mail,
                fontSize = 14.sp,
                color = Color.DarkGray,
            )
            IconButton(onClick = {
                val con = Gson().toJson(contact) // on
                onNavigate(Screen.NewContactScreen.route + "/$con")
                // convertit la classe en chaine String
            }) {
                Icon(Icons.Filled.Edit, contentDescription = "")
            }
        }
    }
}