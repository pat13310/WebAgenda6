package com.xenatronics.webagenda.presentation.screens.new_rdv

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.common.events.NewRdvEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.common.util.LockScreenOrientation
import com.xenatronics.webagenda.common.util.getDateFormatter
import com.xenatronics.webagenda.common.util.getTimeFormatter
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.presentation.components.NewTaskBar
import com.xenatronics.webagenda.presentation.components.UIComboContact
import com.xenatronics.webagenda.presentation.components.UiDatePicker
import com.xenatronics.webagenda.presentation.components.UiTimePicker
import kotlinx.coroutines.flow.collect

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewRdvScreen(
    navController: NavController,
    viewModel: NewRdvViewModel,
    rdv: Rdv
) {
    val scaffoldState= rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(actionLabel = event.action, message = event.message)
                }
                is UIEvent.PopBackStack -> {

                }
                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                NewTaskBar(if (rdv.id == 0) "Nouveau rendez-vous" else "Modifier rendez-vous",
                    NavigateToListScreen = {
                        if (it == Action.ADD) {
                            if (rdv.id == 0) {
                                //viewModel.setSelectRdv(state)
                                viewModel.onEvent(NewRdvEvent.OnNew)
                            } else {
                                //viewModel.setSelectRdv(state.rdv!!)
                                viewModel.onEvent(NewRdvEvent.OnUpdate)
                            }
                        }
                        if (it == Action.NO_ACTION) {
                            viewModel.onEvent(NewRdvEvent.OnBack)
                        }
                    })
            },
            content = {
                NewRdvContent(
                    navController = navController,
                    viewModel = viewModel,
                    rdv = rdv
                )
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewRdvContent(
    navController: NavController,
    viewModel: NewRdvViewModel,
    rdv: Rdv,
) {
    BoxWithConstraints {
        val constraint = decoupledConstraints(16.dp)
        ConstraintLayout(constraint) {
            LaunchedEffect(key1 = true) {
                viewModel.loadContact()
            }
            val listContact = viewModel.allContactFlow.collectAsState()
            var timestamp = viewModel.calendar.value.timeInMillis
            if (rdv.nom.isNotBlank()) {
                timestamp = rdv.date
            }

            Image(
                painterResource(id = R.drawable.rdv2), contentDescription = "",
                Modifier
                    .padding(vertical = 20.dp)
                    .scale(1.5f)
                    .layoutId("image")
            )
            UIComboContact(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textRdv"),
                options = listContact.value.toList().sortedBy { contact -> contact.nom },
                onNavigate = { route ->
                    navController.navigate(route = route)
                },
                viewModel = viewModel,
                text = rdv.nom
            )

            UiDatePicker(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textDate"),
                viewModel = viewModel,
                text = getDateFormatter(timestamp),
                rdv = rdv,
            )
            UiTimePicker(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textTime"),
                viewModel = viewModel,
                text = getTimeFormatter(timestamp)
            )
        }
    }
}

private fun decoupledConstraints(margin: Dp, hMargin: Dp = 16.dp): ConstraintSet {
    return ConstraintSet {
        val image = createRefFor("image")
        val textRdv = createRefFor("textRdv")
        val textDate = createRefFor("textDate")
        val textTime = createRefFor("textTime")

        constrain(image) {
            top.linkTo(parent.top, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textRdv) {
            top.linkTo(image.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textDate) {
            top.linkTo(textRdv.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textTime) {
            top.linkTo(textDate.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
    }
}