package com.xenatronics.webagenda.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.util.Constants
import com.xenatronics.webagenda.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.util.calendarSetTime
import com.xenatronics.webagenda.util.getTimeFormatter
import com.xenatronics.webagenda.viewmodel.ViewModelRdv
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UiTimePicker(
    rdv: Rdv,
    text:String,
    viewModel: ViewModelRdv,
    modifier: Modifier,
    borderColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.primary,
) {
    Locale.setDefault(Locale.FRANCE)
    val calendar by viewModel.calendar
    val timeTmp = rememberSaveable{ mutableStateOf(getTimeFormatter(calendar.timeInMillis)) }
    LaunchedEffect(true){
        if (rdv.id>0){
            timeTmp.value=text
        }
    }
    val dlg = showTimeDialog(timeTmp)
    calendarSetTime(time = timeTmp.value, calendar = calendar)
    rdv.date=calendar.timeInMillis
    viewModel.selectRdv.value=rdv.copy()
    Box(
        modifier = modifier
            .background(Color.White)
            //.padding(16.dp)
            .height(HEIGHT_COMPONENT)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Constants.RADIUS_MEDIUM),
            )
            .clickable {
                dlg.show()
            }
    ) {
        Row(
            modifier = modifier.padding(start = 28.dp, end =0.dp, top=3.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(
                modifier = Modifier.weight(11f),
                text = timeTmp.value,
                color = textColor,
            )
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {dlg.show()}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = "dateIcon",
                    tint = iconColor
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showTimeDialog(time: MutableState<String>): MaterialDialogState {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Annuler")
        }
    ) {
        timepicker(
            title = "Choisir l'heure",
            is24HourClock = true,
            initialTime = LocalTime.parse(time.value)
        )
        {
            time.value = it.format(formatter)
        }
    }
    return dialogState
}


