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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.data.ResponseContact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Constants
import com.xenatronics.webagenda.util.Constants.FADE_IN_ANIMATION_DURATION
import com.xenatronics.webagenda.util.Constants.FADE_OUT_ANIMATION_DURATION

@SuppressLint("UnusedTransitionTargetStateParameter", "UnrememberedMutableState")
@Composable
fun ExpandableContactCard(
    contact: ResponseContact,
    onCardArrowClick: () -> Unit,
    onClickItem: (ResponseContact) -> Unit,
    expanded: Boolean,
    selected:Boolean,
    navController: NavController

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
        if (expanded) Color.White else MaterialTheme.colors.secondary
    }
    val cardFgColor by transition.animateColor({
        tween(durationMillis = Constants.EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) Color.Black else colorResource(id = R.color.purple_900)
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
    var isSelected = remember {mutableStateOf(false)}
    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        border = BorderStroke(
            width = 1.dp,
            if (!selected) Color.LightGray else MaterialTheme.colors.primary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(cardPaddingHorizontal, 16.dp, end = cardPaddingHorizontal, bottom = 0.dp)
            .clickable {
                isSelected.value = !isSelected.value
                onClickItem(contact)
            },

    ) {
        Column(Modifier.fillMaxSize()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .padding(start = 0.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                ) {
                CardArrow(
                    modifier = Modifier.fillMaxWidth(0.1f),
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardContactTitle(
                    modifier = Modifier.fillMaxWidth(0.65f),
                    expanded = expanded,
                    title = contact.nom,
                    tel = contact.tel,
                    color = cardFgColor
                )
            }

        }
        ExpandableContactContent(
            contact=contact,
            visible = expanded,
            initialVisibility = expanded,
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContactContent(
    navController: NavController,
    contact:ResponseContact,
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
                text = contact.adresse,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Text(
                text = contact.cp + " " + contact.ville,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Text(
                text = contact.tel,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Row (Modifier.fillMaxWidth()){
                Text(
                    modifier=Modifier.fillMaxWidth(0.88f),
                    text = contact.mail,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                IconButton(onClick = {
                    val contact=Gson().toJson(contact ) // on convertit la classe en chaine String
                    navController.navigate(Screen.NewContactScreen.route+"/$contact")
                }) {
                    Icon(Icons.Filled.Edit, contentDescription = "")
                }
            }
        }
    }
}

@Composable
fun CardContactTitle(
    modifier: Modifier,
    expanded: Boolean,
    title: String,
    tel: String,
    color: Color,
) {
    Text(
        modifier = modifier,
        color = color,
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left
    )
    if (!expanded) {
        //Spacer(modifier = Modifier.width(100.dp))
        Text(
            modifier = Modifier.alpha(0.9f),
            color = color,
            text = tel,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Left
        )
    }
}
