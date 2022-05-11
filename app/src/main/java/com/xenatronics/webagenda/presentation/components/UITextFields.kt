package com.xenatronics.webagenda.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.xenatronics.webagenda.common.util.Constants
import com.xenatronics.webagenda.common.util.Constants.RADIUS_SMALL
import com.xenatronics.webagenda.common.util.Constants.TOP_SPACE


@ExperimentalComposeUiApi
@Composable
fun UITextStandard(
    modifier: Modifier,
    label: String = "",
    value: String,
    onTextChanged: (String) -> Unit,
    icon: ImageVector = Icons.Default.Place,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = 35,
    maxLines: Int = 1,
    padding: Dp = TOP_SPACE,
    focusNext: Boolean = true,
    focus: Boolean = false,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var visibility by remember { mutableStateOf(keyboardType != KeyboardType.Password) }

    LaunchedEffect(focus) {
        if (focus) {
            focusRequester.requestFocus()
        }
    }

    OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            leadingIconColor = MaterialTheme.colors.primary,
            trailingIconColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.primary.copy(alpha = 0.50f)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            if (focusNext)
                focusManager.moveFocus(FocusDirection.Down)
            else
                focusManager.clearFocus(true)
        }),
        value = value,
        maxLines = maxLines,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon"
            )
        },
        onValueChange = {
            if (it.length < maxLength) {
                onTextChanged(it)
            }
        },
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                IconButton(onClick = { visibility = !visibility }) {
                    Icon(
                        imageVector = if (visibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Password"
                    )
                }
            }
        },
        placeholder = { Text(text = label) },
        shape = RoundedCornerShape(RADIUS_SMALL),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = padding)
            .height(Constants.HEIGHT_COMPONENT)
            .focusRequester(focusRequester),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}
