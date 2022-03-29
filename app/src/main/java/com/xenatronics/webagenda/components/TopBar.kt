package com.xenatronics.webagenda.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.R


@Composable
fun NewTaskBar(
    title:String="",
    NavigateToListScreen: (Action) -> Unit,
    noBack:Boolean=false,
    action:Boolean=true
) {
    TopAppBar(
        elevation = 8.dp,
        navigationIcon = {
            if (!noBack) {
                BackAction(onBackClicked = NavigateToListScreen)
            }
        },
        title = {
            Text(
                modifier=Modifier.fillMaxWidth(0.9f),
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            if (action)
                ValidateAction(onValidateClicked = NavigateToListScreen)
        }
    )
}

@Composable
fun ListTaskBar(
    title:String,
    NavigateToListScreen: (Action) -> Unit,
    closeAction:Boolean=true,
    valideAction:Boolean=true,
    deleteAction:Boolean=true,
) {
    TopAppBar(
        elevation = 2.dp,
        navigationIcon = {
            if (closeAction)
                CloseAction(onCloseClicked = NavigateToListScreen)
        },
        title = {
            Text(
                modifier=Modifier.fillMaxWidth(0.9f),
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            if (valideAction)
                ValidateAction(onValidateClicked = NavigateToListScreen)
            if (deleteAction)
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