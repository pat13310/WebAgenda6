package com.xenatronics.webagenda.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MenuOption(
    title: String,
    onDelete: () -> Unit,
    onDeleteAll: () -> Unit,
    onLogout: () -> Unit,
) {
    val context = LocalContext.current
    val isExpanded = remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(
                color = Color.White,
                modifier = Modifier.fillMaxWidth(0.9f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                text = title
            )
        },
        actions = {
            ActionDelete(onDelete = onDelete)
            ActionMenuShow(onShow = { isExpanded.value = !isExpanded.value })
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(0.6f),
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false }) {
                DropdownMenuItem(onClick = {
                    isExpanded.value = false
                    onLogout()
                }) {
                    Text(text = "Déconnexion", modifier = Modifier.weight(5f))
                    Icon(Icons.Filled.Logout, contentDescription = "")
                }
                DropdownMenuItem(onClick = {
                    isExpanded.value = false
                    onDeleteAll()
                }) {
                    Text("Effacer tout", modifier = Modifier.weight(1f))
                    Icon(Icons.Filled.Delete, contentDescription = "")
                }
            }
        }
    )
}

@Composable
fun ActionLogout(
    onLogout: () -> Unit
) {
    IconButton(onClick = onLogout) {
        Icons.Filled.Logout
    }
}

@Composable
fun ActionDelete(
    onDelete: () -> Unit
) {
    IconButton(onClick = onDelete) {
        Icon(
            Icons.Filled.Delete,
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
fun ActionMenuShow(
    onShow: () -> Unit
) {
    IconButton(onClick = {
        onShow()
    }) {
        Icon(
            Icons.Filled.MoreVert,
            contentDescription = "",
            tint = Color.White
        )
    }
}

