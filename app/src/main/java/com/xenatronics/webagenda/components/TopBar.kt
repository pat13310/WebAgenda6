package com.xenatronics.webagenda.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xenatronics.webagenda.Action
import com.xenatronics.webagenda.R


@Composable
fun NewTaskBar(
    title:String="",
    NavigateToListScreen: (Action) -> Unit,
    noBack:Boolean=false,
) {
    TopAppBar(
        elevation = 12.dp,
        navigationIcon = {
            if (!noBack) {
                BackAction(onBackClicked = NavigateToListScreen)
            }
        },
        title = {
            Text(
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            ValidateAction(onValidateClicked = NavigateToListScreen)
        }
    )
}

@Composable
fun ListTaskBar(
    title:String,
    NavigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        elevation = 12.dp,
        navigationIcon = {
            CloseAction(onCloseClicked = NavigateToListScreen)
        },
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            ValidateAction(onValidateClicked = NavigateToListScreen)
            DeleteAction(onDeleteClicked = NavigateToListScreen)
        }
    )
}


@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            tint = Color.White
        )
    }
}

@Composable
fun ValidateAction(
    onValidateClicked: (Action) -> Unit
) {
    IconButton(onClick = { onValidateClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.back),
            tint = Color.White
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: (Action) -> Unit
) {
    IconButton(onClick = { onDeleteClicked(Action.DELETE) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.back),
            tint = Color.White
        )
    }
}

@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.back),
            tint = Color.White
        )
    }
}