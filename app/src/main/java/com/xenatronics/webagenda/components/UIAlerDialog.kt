package com.xenatronics.webagenda.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.xenatronics.webagenda.R

@Composable
fun UIAlertDialog(
    title: String,
    message: String,
    onValidate: () -> Unit,
    onCancel: () -> Unit,

    ) {
    var openDialog by remember {
        mutableStateOf(true)
    }
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                Button(onClick = onValidate) {
                    Text(text = stringResource(id = R.string.confirm))
                    openDialog=false
                }
            },
            dismissButton = {
                Button(onClick = onCancel) {
                    Text(text = stringResource(id = R.string.cancel))
                    openDialog=false
                }
            }
        )
}