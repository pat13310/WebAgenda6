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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.vanpra.composematerialdialogs.title
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun UiDatePicker(

    texte: String,
    updateDialogDate: (String) -> Unit
) {
    TextDateOutField(
        texte = texte,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        //onChangedDate = updateDialogDate
    )
}

@Composable
fun TextDateOutField(
    texte: String="",
    modifier: Modifier,
    borderColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.primary,
    //onChangedDate: (String) -> Unit
) {
    Locale.setDefault(Locale.FRANCE)

    val date = remember { mutableStateOf("12 mars 2022") }
    val dlg = showDialogDate(date)

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(50),
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
                text = date.value,
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
    UiDatePicker( "Aujourd'hui", updateDialogDate = {})
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
fun showDialogDate(date:MutableState<String>): MaterialDialogState {
    val formatter = DateTimeFormatter.ofPattern(  "dd LLLL yyyy", Locale.FRANCE)
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

            date.value=it.format(formatter)
        }
    }
    return dialogState
}

@Composable
fun ShowUIDialogDate(

    //    val calendar = Calendar.getInstance(Locale.getDefault())
//    val day = calendar[Calendar.DAY_OF_MONTH]
//    val month = calendar[Calendar.MONTH]
//    val year = calendar[Calendar.YEAR]
//    val date = remember { mutableStateOf("") }
    day: Int,
    month: Int,
    year: Int,
    date: MutableState<String> = mutableStateOf("")
) {
    DatePickerDialog(
        LocalContext.current, { _, y: Int, m: Int, dayof: Int ->
            if (m < 10) {
                date.value = dayof.toString() + "/0" + (m + 1) + "/" + y
            } else
                date.value = dayof.toString() + "/" + (m + 1) + "/" + y
        },
        year, month, day
    ).show()
}