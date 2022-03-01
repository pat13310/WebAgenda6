package com.xenatronics.webagenda.components

import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun UiDatePicker(activate: Boolean,
                 texte: String,
                 updateDialogDate:(Long)->Unit
) {
    TextDateOutField(text = texte,
        modifier = Modifier.fillMaxWidth(),
        updatedDate = updateDialogDate
    )
}

@Composable
fun TextDateOutField(
    text: String,
    modifier: Modifier,
    borderColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.primary,
    updatedDate: (Long) -> Unit
) {
    val activity = LocalContext.current as AppCompatActivity
    Box(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(50),
            )
            .clickable {
                showDatePicker(activity = activity, updatedDate =updatedDate )
            }
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
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
    UiDatePicker(true, "Aujourd'hui", updateDialogDate = {})
}

fun DateFormater(milliseconds : Long?) : String?{
    milliseconds?.let{
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
    return null
}
private fun showDatePicker(
    activity : AppCompatActivity,
    updatedDate: (Long) -> Unit)
{
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updatedDate(it)
    }
}
