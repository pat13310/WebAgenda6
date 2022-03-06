package com.xenatronics.webagenda.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
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
fun TextPhone(
    textPhone: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textPhone,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "phoneIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        //label = { Text(text = "Téléphone") },
        placeholder = { Text(text = "Numéro de téléphone") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}

@Composable
fun TextCP(
    textCP: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textCP,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "CPIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        //label = { Text(text = "Code postal") },
        placeholder = { Text(text = "Code postal") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}

@Composable
fun TextStandard(
    label:String="",
    textStandard: String,
    onTextChanged: (String) -> Unit,
    icon:ImageVector=Icons.Default.Place,
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
fun TextNom(
    textNom: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textNom,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "CPIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        label = { Text(text = "Nom") },
        placeholder = { Text(text = "Nom du rendez-vous") },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}

