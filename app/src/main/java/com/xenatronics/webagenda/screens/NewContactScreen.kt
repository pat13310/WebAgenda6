package com.xenatronics.webagenda.screens

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xenatronics.webagenda.util.Action
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UITextStandard
import com.xenatronics.webagenda.data.Contact
import com.xenatronics.webagenda.data.PostContact
import com.xenatronics.webagenda.data.ResponseContact
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.LockScreenOrientation
import com.xenatronics.webagenda.viewmodel.ViewModelContact


@Composable
fun NewContactScreen(
    navController: NavController,
    contact: ResponseContact,
    viewModel: ViewModelContact = hiltViewModel()
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Scaffold(
        topBar = {
            NewTaskBar(if (contact.id == 0) "Nouveau Contact" else "Modifier un Contact",
                NavigateToListScreen = { action ->
                    when (action) {
                        Action.ADD -> {
                            // new contact
                            if (contact.id == 0) {
                                val post = PostContact(
                                    contact.nom,
                                    contact.adresse,
                                    contact.cp,
                                    contact.ville,
                                    contact.tel,
                                    contact.mail
                                )
                                viewModel.AddContact(contact = post)
                            } else { // update contact
                                val update = Contact(
                                    contact.id,
                                    contact.nom,
                                    contact.adresse,
                                    contact.cp,
                                    contact.ville,
                                    contact.tel,
                                    contact.mail
                                )
                                viewModel.UpdateContact(contact = update)
                            }
                            navController.navigate(Screen.ListContactScreen.route)
//                        val post=PostRequest( viewModel.nom.value, viewModel.timestamp.value)
//                        viewModel.AddRdv(post)
                        }
                        Action.NO_ACTION -> {
                            navController.popBackStack()
                        }
                        else -> {}
                    }
                })
        },
        content = {
            ContactContent(viewModel = hiltViewModel(), contact)
        }
    )
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun ContactContent(

    viewModel: ViewModelContact,
    contact: ResponseContact
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp, top = 5.dp, start = 8.dp, end = 8.dp)
    ) {
        var nom by remember { mutableStateOf(contact.nom) }
        var adresse by remember { mutableStateOf(contact.adresse) }
        var cp by remember { mutableStateOf(contact.cp) }
        var ville by remember { mutableStateOf(contact.ville) }
        var tel by remember { mutableStateOf(contact.tel) }
        var mail by remember { mutableStateOf(contact.mail) }

        UITextStandard(
            label = "Rendez-vous",
            value = nom,
            onTextChanged = {
                nom = it
                contact.nom = it
            },
            icon = Icons.Default.Person
        )
        Spacer(modifier = Modifier.height(5.dp))
        UITextStandard(
            label = "Adresse",
            value = adresse,
            onTextChanged = {
                adresse = it
                contact.adresse = it
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        UITextStandard(
            label = "Ville",
            value = ville,
            onTextChanged = {
                ville = it
                contact.ville = it
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        UITextStandard(
            label = "Code Postal",
            value = cp,
            onTextChanged = {
                cp = it
                contact.cp = it
            },
            icon = Icons.Default.Place,
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(5.dp))
        UITextStandard(
            label = "Téléphone",
            value = tel,
            onTextChanged = {
                tel = it
                contact.tel = it
            },
            icon = Icons.Default.Phone,
            keyboardType = KeyboardType.Phone,
            focusNext = false
        )
        Spacer(modifier = Modifier.height(5.dp))
        UITextStandard(
            label = "Adresse Mail",
            value = mail,
            onTextChanged = {
                mail = it
                contact.mail = it
            },
            icon = Icons.Default.Email,
        )

    }
}

