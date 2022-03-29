package com.xenatronics.webagenda.components

import android.annotation.SuppressLint
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.xenatronics.webagenda.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CardArrow(
    modifier: Modifier,
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        modifier=modifier,
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.drop_up),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}

@SuppressLint("SimpleDateFormat")
fun convertTime(timestamp: Long): String {
    val simpleDateFormat =
        SimpleDateFormat("'Le' dd MMMM yyyy 'à' HH:mm", Locale.FRANCE)
    return simpleDateFormat.format(timestamp )
}
