package com.xenatronics.webagenda.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.Action
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.components.UITextStandard
import com.xenatronics.webagenda.viewmodel.ViewModelContact

val TOP_SPACE = 6.dp

@Composable
fun ContactActivity(navController: NavController) {
    Scaffold(
        topBar = {
            NewTaskBar("Ajouter vos Contacts", NavigateToListScreen = { action ->
                when (action) {
//                    Action.ADD->{
//                        val post=PostRequest( viewModel.nom.value, viewModel.timestamp.value)
//                        viewModel.AddRdv(post)
//                    }
                    Action.NO_ACTION -> {
                        navController.popBackStack()
                    }
                }
            })
        },
        content = { ContactContent(viewModel = ViewModelContact()) }
    )
}


@Composable
fun ContactContent(viewModel: ViewModelContact) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp, top = 0.dp, start = 8.dp, end = 8.dp)
    ) {
        var nom by viewModel.nom
        var adresse by viewModel.adresse
        var cp by viewModel.cp
        var ville by viewModel.ville
        var mail by viewModel.mail
        var phone by viewModel.tel

        UITextStandard(
            label = "Rendez-vous",
            value = nom,
            onTextChanged = { nom = it },
            icon = Icons.Default.Person
        )
        UITextStandard(
            label = "Adresse",
            value = adresse,
            onTextChanged = { adresse = it }
        )
        UITextStandard(
            label = "Ville",
            value = ville,
            onTextChanged = { ville = it }
        )
        UITextStandard(
            label = "Code Postal",
            value = cp,
            onTextChanged = { cp = it },
            icon = Icons.Default.Place,
            keyboardType = KeyboardType.Number
        )
        UITextStandard(
            label = "Adresse Mail",
            value = mail,
            onTextChanged = { mail = it },
            icon = Icons.Default.Email,
        )
        UITextStandard(
            label = "Téléphone",
            value = phone,
            onTextChanged = { phone = it },
            icon = Icons.Default.Phone,
            keyboardType = KeyboardType.Phone
        )
    }
}

