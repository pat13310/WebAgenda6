package com.xenatronics.webagenda.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.xenatronics.webagenda.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.viewmodel.ViewModelRdv
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun UiTimePicker(
    viewModel: ViewModelRdv,
    texte: String = "",
    modifier: Modifier = Modifier.fillMaxWidth(),
    borderColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.primary,

    ) {
    Locale.setDefault(Locale.FRANCE)
    val calendar = Calendar.getInstance(Locale.FRANCE)
    val hour = calendar[Calendar.HOUR]
    val minute = calendar[Calendar.MINUTE]

    var time by viewModel.time
    val timeTmp = remember { mutableStateOf(time) }
    val dlg = showTimeDialog(timeTmp)
    time = timeTmp.value

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
            .height(HEIGHT_COMPONENT)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.large,
            )
            .clickable {
                dlg.show()
            }
    ) {
        Row(
            modifier = modifier.padding(horizontal = 28.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = timeTmp.value,
                color = textColor,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "dateIcon",
                tint = iconColor
            )
        }
    }
}


@Composable
fun showTimeDialog(time: MutableState<String>): MaterialDialogState {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.FRANCE)
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
        )//, initialTime = LocalTime.parse(time.value, formatter))
        {
            time.value = it.format(formatter)
        }
    }
    return dialogState
}


