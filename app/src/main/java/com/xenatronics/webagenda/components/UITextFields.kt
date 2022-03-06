package com.xenatronics.webagenda.components

import androidx.compose.foundation.layout.fillMaxWidth
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


@Composable
fun TextMail(
    textMail: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textMail,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "emailIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        //label = { Text(text = "Email") },
        placeholder = { Text(text = "Adresse mail") },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}


@Composable
fun TextStandard(
    label: String = "",
    textStandard: String,
    onTextChanged: (String) -> Unit,
    icon: ImageVector = Icons.Default.Place,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = textStandard,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        //label = { Text(text = label) },
        placeholder = { Text(text = label) },
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE),
    )
}

@Composable
fun UITextPassword(
    label: String = "Mot de passe",
    textNom: String,
    onTextChanged: (String) -> Unit,
    icon: ImageVector = Icons.Default.Password,
) {
    var visibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textNom,
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
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}

