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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.components.ExpandableCard
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.util.Constants.EXPAND_ANIMATION_DURATION
import com.xenatronics.webagenda.viewmodel.ViewmodelRdv
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CardActivity(navController: NavController) {
    Scaffold(
        topBar = {
            ListTaskBar(NavigateToListScreen = {navController.popBackStack()})
        },
        content = {
            CardContent(navController = navController, viewModel = viewModel() as ViewmodelRdv)
        }
    )
}


@Composable
fun CardContent(navController: NavController, viewModel:ViewmodelRdv) {

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
                    onCardArrowClick = { viewModel?.onCardArrowClicked(it.id) },
                    expanded = expandedCardIds.value.contains(it.id),
                )
            }
        }
    }
}

