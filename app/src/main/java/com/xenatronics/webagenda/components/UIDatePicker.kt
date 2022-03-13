package com.xenatronics.webagenda.components

import android.app.DatePickerDialog
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.xenatronics.webagenda.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.util.Constants.RADIUS_MEDIUM
import com.xenatronics.webagenda.viewmodel.ViewModelAdd
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun UiDatePicker(
    viewModel: ViewModelAdd,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(Color.White),
    borderColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.primary,
) {
    Locale.setDefault(Locale.FRANCE)

    var date by viewModel.date
    val datetmp = remember { mutableStateOf(date) }
    val dlg = showDialogDate(datetmp)
    date = datetmp.value

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height( HEIGHT_COMPONENT)
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
                text = datetmp.value,
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

@Preview
@Composable
fun DatePreview() {
    //UiDatePicker()
}

fun dateFormatter(milliseconds: Long?): String {
    milliseconds?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
    return ""
}

@Composable
fun showDialogDate(date: MutableState<String>): MaterialDialogState {
    val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy", Locale.FRANCE)
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Annuler")
        }
    ) {
        datepicker(title = "Choisir une date", initialDate = LocalDate.parse(date.value, formatter))
        {
            date.value = it.format(formatter)
        }
    }
    return dialogState
}

