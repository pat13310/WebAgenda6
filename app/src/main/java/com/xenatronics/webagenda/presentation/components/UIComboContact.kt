package com.xenatronics.webagenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.domain.model.Contact
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.common.util.Constants.RADIUS_MEDIUM
import com.xenatronics.webagenda.presentation.screens.new_rdv.ViewModelNewRdv


@Composable
fun UIComboContact(
    modifier: Modifier,
    viewModel: ViewModelNewRdv,
    options: List<Contact>,
    onNavigate: (String) -> Unit,
    onContact: (Contact) -> Unit,
    text: String,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val textOption = remember { mutableStateOf("") }
    if (text.isNotEmpty())
        textOption.value = text
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(HEIGHT_COMPONENT)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(RADIUS_MEDIUM),
                )
                .padding(top = 16.dp, start = 10.dp, end = 18.dp, bottom = 16.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    expanded = true
                }) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = ""
                )
            }
            Text(
                modifier = Modifier
                    .weight(6f)
                    .background(Color.White)
                    .pointerInput(this) {

                    },
                text = textOption.value,
                color = MaterialTheme.colors.primary
            )
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    expanded = false
                    onNavigate(Screen.ListContactScreen.route)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_add),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = ""
                )
            }
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(0.75f),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                options.forEach { contact ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.selectContact.value = contact
                            onContact(contact)
                            textOption.value = contact.nom
                            expanded = false
                        }
                    ) {
                        Text(
                            text = contact.nom,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}
