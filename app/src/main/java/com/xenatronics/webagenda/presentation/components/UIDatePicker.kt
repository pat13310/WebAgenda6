package com.xenatronics.webagenda.presentation.components

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
import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.common.util.Constants
import com.xenatronics.webagenda.common.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.common.util.calendarSetDate
import com.xenatronics.webagenda.common.util.getDateFormatter
import com.xenatronics.webagenda.presentation.viewmodel.ViewModelRdv
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
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
            .fillMaxWidth()
            .background(Color.White)
            .height(HEIGHT_COMPONENT)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Constants.RADIUS_MEDIUM)
            )
            .clickable {
                dlg.show()
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 28.dp,end=0.dp, top=3.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(
                modifier= Modifier.weight(11f),
                text = dateState.value,
                color = textColor,
            )
            IconButton(
                modifier= Modifier.weight(1f),
                onClick = {
                    dlg.show()
                }) {
                Icon(

                    Icons.Default.DateRange,
                    contentDescription = "dateIcon",
                    tint = iconColor
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
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

