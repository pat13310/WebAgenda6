package com.xenatronics.webagenda.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.xenatronics.webagenda.activities.TOP_SPACE
import com.xenatronics.webagenda.util.Constants
import com.xenatronics.webagenda.util.Constants.RADIUS_MEDIUM
import com.xenatronics.webagenda.util.Constants.RADIUS_SMALL


@Composable
fun UITextStandard(
    label: String = "",
    value: String,
    onTextChanged: (String) -> Unit,
    icon: ImageVector = Icons.Default.Place,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(text = label) },
        shape = RoundedCornerShape(RADIUS_SMALL),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
            .height(Constants.HEIGHT_COMPONENT),
    )
}

@Composable
fun UITextPassword(
    label: String = "Mot de passe",
    value: String,
    onTextChanged: (String) -> Unit,
    icon: ImageVector = Icons.Default.VpnKey,
) {
    var visibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = { visibility = !visibility }) {
                Icon(
                    imageVector = if (visibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Password"
                )
            }
        },
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(text = label) },
        shape = RoundedCornerShape(RADIUS_SMALL),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
        .height(Constants.HEIGHT_COMPONENT),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}

