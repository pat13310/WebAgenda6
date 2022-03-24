package com.xenatronics.webagenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.util.Constants.RADIUS_MEDIUM
import com.xenatronics.webagenda.viewmodel.ViewModelRdv


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UIComboContact(
    viewModel: ViewModelRdv,
    options: List<String>,
    onNavigate: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by viewModel.nom

    Box(
        modifier = Modifier
            .padding(all = 16.dp)
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
                text = selectedOptionText,
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
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(
                            text = selectionOption,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}

