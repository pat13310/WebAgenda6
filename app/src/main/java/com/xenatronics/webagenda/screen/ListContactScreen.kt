package com.xenatronics.webagenda.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.components.ExpandableContactCard
import com.xenatronics.webagenda.components.ListTaskBar
import com.xenatronics.webagenda.data.ResponseContact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.viewmodel.ViewModelContact

@Composable
fun ListContactScreen(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val contact =
                        Gson().toJson(ResponseContact()) // on convertit la classe en chaine String
                    navController.navigate(Screen.NewContactScreen.route + "/$contact")
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add contact"
                )
            }
        },
        topBar = {
            ListTaskBar("Vos contacts", NavigateToListScreen = {
                navController.popBackStack()
            })
        },
        content = {
            ListContactContent(viewModel = hiltViewModel(), navController = navController)
        }
    )
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun ListContactContent(
    navController: NavController,
    viewModel: ViewModelContact
) {
    val contacts = viewModel.allContactFlow.collectAsState()
    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color(
            ContextCompat.getColor(
                LocalContext.current,
                R.color.white
            )
        )
    ) {
        val selectedItem = mutableStateOf(ResponseContact())
        LazyColumn(Modifier.fillMaxSize()) {
            items(contacts.value) { item ->
                ExpandableContactCard(
                    selected = selectedItem.value == item,
                    contact = item,
                    onCardArrowClick = { viewModel.onCardArrowClicked(item.id) },
                    expanded = expandedCardIds.value.contains(item.id),
                    onClickItem = {
                        selectedItem.value = it
                    },
                    navController = navController
                )
            }
        }
    }
}
