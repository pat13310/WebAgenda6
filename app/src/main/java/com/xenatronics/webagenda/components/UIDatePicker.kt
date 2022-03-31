package com.xenatronics.webagenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.xenatronics.webagenda.data.Rdv
import com.xenatronics.webagenda.util.Constants
import com.xenatronics.webagenda.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.util.calendarSetDate
import com.xenatronics.webagenda.util.getDateFormatter
import com.xenatronics.webagenda.viewmodel.ViewModelRdv
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun UiDatePicker(
    rdv: Rdv,
    text: String,
    viewModel: ViewModelRdv,
    modifier: Modifier,
    borderColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.primary,
) {
    Locale.setDefault(Locale.FRANCE)
    val calendar by viewModel.calendar
    val dateState = rememberSaveable { mutableStateOf(getDateFormatter(calendar.timeInMillis)) }
    LaunchedEffect(true) {
        if (rdv.id>0) {
            dateState.value = text
        }
    }
    val dlg = showDialogDate(dateState)
    calendarSetDate(date = dateState.value, calendar = calendar)
    rdv.date = calendar.timeInMillis
    viewModel.selectRdv.value = rdv.copy()

   // val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(HEIGHT_COMPONENT)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Constants.RADIUS_MEDIUM)
            )
            .clickable {
                //scope.launch {
                    dlg.show()
                //}
            }
    ) {
        Row(
            modifier = modifier.padding(horizontal = 28.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dateState.value,
                color = textColor,
            )
            Icon(
                Icons.Default.DateRange,
                contentDescription = "dateIcon",
                tint = iconColor
            )
        }
    }
}


@Composable
fun showDialogDate(date: MutableState<String>): MaterialDialogState {
    val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy", Locale.getDefault())
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Annuler")
        }
    ) {
        datepicker(
            title = "Choisir une date",
            initialDate = LocalDate.parse(date.value, formatter)
        ) {
            date.value = it.format(formatter)
        }
    }
    return dialogState
}

