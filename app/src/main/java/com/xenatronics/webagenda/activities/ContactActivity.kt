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
import com.xenatronics.webagenda.components.TextStandard
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


        TextStandard(
            label = "Rendez-vous",
            textStandard = nom,
            onTextChanged = { nom = it },
            icon = Icons.Default.Person
        )
        TextStandard(
            label = "Adresse",
            textStandard = adresse,
            onTextChanged = { adresse = it }
        )
        TextStandard(
            label = "Ville",
            textStandard = ville,
            onTextChanged = { ville = it }
        )
        TextStandard(
            label = "Code Postal",
            textStandard = cp,
            onTextChanged = { cp = it },
            icon = Icons.Default.Place,
            keyboardType = KeyboardType.Number
        )
        TextStandard(
            label = "Adresse Mail",
            textStandard = mail,
            onTextChanged = { mail = it },
            icon = Icons.Default.Email,
        )
        TextStandard(
            label = "Téléphone",
            textStandard = phone,
            onTextChanged = { phone = it },
            icon = Icons.Default.Phone,
            keyboardType = KeyboardType.Phone
        )

    }
}

