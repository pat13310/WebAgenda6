package com.xenatronics.webagenda.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xenatronics.webagenda.R

@ExperimentalMaterialApi
@Composable
fun UIAuthButton(
    defaultText: String = stringResource(R.string.GoogleConnect),
    loadingText: String = stringResource(R.string.CreateAccount),
    icon: Painter = painterResource(id = R.drawable.ic_google),
    shape: Shape = RoundedCornerShape(4.dp),
    onClick: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }
    Surface(
        onClick = {
            clicked = !clicked
            onClick()
        },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                //.height(38.dp)
                .padding(end = 8.dp, start = 4.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = "",
                tint = Color.Unspecified,
                //modifier = Modifier.scale(0.60f)
            )
            Spacer(Modifier.width(6.dp))
            Text(if (clicked) loadingText else defaultText)
            if (clicked) {
                Spacer(modifier = Modifier.width(4.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun PreviewButtonGoogle() {
    UIAuthButton(onClick = {})
}