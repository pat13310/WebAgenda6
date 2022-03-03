package com.xenatronics.webagenda.components



import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.xenatronics.webagenda.R
import java.util.*


@Composable
fun UiTimePicker(

    texte: String,
    //updateTime: (Long) -> Unit
) {
    TextTimeOutField(
        text = texte,
        modifier = Modifier.fillMaxWidth(),
        //updateTime = updateTime
    )
}

@Composable
fun TextTimeOutField(
    text: String="",
    modifier: Modifier,
    borderColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.primary,
    //updateTime: (Long) -> Unit
) {

    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR]
    val minute = calendar[Calendar.MINUTE]
    val time = remember { mutableStateOf("") }


    val timePickerDialog = TimePickerDialog(
        LocalContext.current, { _, h: Int, m: Int ->
            time.value = "$h:$m"
        },
        hour, minute, true
    )

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
                timePickerDialog.show()
            }
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = time.value,
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

@Preview
@Composable
fun TimePreview() {
    UiTimePicker( "Aujourd'hui")
}

