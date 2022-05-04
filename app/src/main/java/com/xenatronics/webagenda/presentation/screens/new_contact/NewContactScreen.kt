package com.xenatronics.webagenda.presentation.screens.new_contact

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.xenatronics.webagenda.common.events.NewContactEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.common.util.LockScreenOrientation
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.presentation.components.NewTaskBar
import com.xenatronics.webagenda.presentation.components.UITextStandard
import kotlinx.coroutines.flow.collect


@ExperimentalComposeUiApi
@Composable
fun NewContactScreen(
    navController: NavController,
    viewModel: ViewModelNewContact,
    contact: Contact
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        topBar = {
            NewTaskBar(if (contact.id == 0) "Nouveau Contact" else "Modifier un Contact",
                NavigateToListScreen = { action ->
                    when (action) {
                        Action.ADD -> {
                            // new contact
                            if (contact.id == 0) {
                                viewModel.setCurrent(contact)
                                viewModel.OnEvent(NewContactEvent.OnNew)
                            } else { // update contact
                                viewModel.setCurrent(contact)
                                viewModel.OnEvent(NewContactEvent.OnUpdate)
                            }
                        }
                        Action.NO_ACTION -> {
                            viewModel.OnEvent(NewContactEvent.OnBack)
                        }
                        else -> Unit
                    }
                })
        },
        content = {
            ContactContent(contact)
        }
    )
}


@ExperimentalComposeUiApi
@Composable
fun ContactContent(
    contact: Contact
) {
    BoxWithConstraints {
        val constraint = decoupledConstraints(0.dp)

        ConstraintLayout(constraint) {
            var nom by remember { mutableStateOf(contact.nom) }
            var adresse by remember { mutableStateOf(contact.adresse) }
            var cp by remember { mutableStateOf(contact.cp) }
            var ville by remember { mutableStateOf(contact.ville) }
            var tel by remember { mutableStateOf(contact.tel) }
            var mail by remember { mutableStateOf(contact.mail) }

            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textNom"),
                label = "Rendez-vous",
                value = nom,
                onTextChanged = {
                    nom = it
                    contact.nom = it
                },
                icon = Icons.Default.Person
            )
            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textAdresse"),
                label = "Adresse",
                value = adresse,
                onTextChanged = {
                    adresse = it
                    contact.adresse = it
                }
            )
            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textVille"),
                label = "Ville",
                value = ville,
                onTextChanged = {
                    ville = it
                    contact.ville = it
                }
            )
            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textCP"),
                label = "Code Postal",
                value = cp,
                onTextChanged = {
                    cp = it
                    contact.cp = it
                },
                icon = Icons.Default.Place,
                keyboardType = KeyboardType.Number
            )
            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textTel"),
                label = "Téléphone",
                value = tel,
                onTextChanged = {
                    tel = it
                    contact.tel = it
                },
                icon = Icons.Default.Phone,
                keyboardType = KeyboardType.Phone,
            )
            UITextStandard(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .layoutId("textMail"),
                label = "Adresse Mail",
                value = mail,
                onTextChanged = {
                    mail = it
                    contact.mail = it
                },
                icon = Icons.Default.Email,
                focusNext = false
            )
        }
    }
}


private fun decoupledConstraints(margin: Dp, hMargin: Dp = 16.dp): ConstraintSet {
    return ConstraintSet {

        val textNom = createRefFor("textNom")
        val textAdresse = createRefFor("textAdresse")
        val textVille = createRefFor("textVille")
        val textCP = createRefFor("textCP")
        val textTel = createRefFor("textTel")
        val textMail = createRefFor("textMail")

        constrain(textNom) {
            top.linkTo(parent.top, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textAdresse) {
            top.linkTo(textNom.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textVille) {
            top.linkTo(textAdresse.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textCP) {
            top.linkTo(textVille.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textTel) {
            top.linkTo(textCP.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
        constrain(textMail) {
            top.linkTo(textTel.bottom, margin = margin)
            start.linkTo(parent.start, margin = hMargin)
            end.linkTo(parent.end, margin = hMargin)
        }
    }
}

