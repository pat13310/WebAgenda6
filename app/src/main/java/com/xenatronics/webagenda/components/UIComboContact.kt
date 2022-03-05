package com.xenatronics.webagenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.viewmodel.ViewModelAdd


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UIComboContact(
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .background(Color.White),
                readOnly = true,

                value = selectedOptionText,
                onValueChange = { },
                //label = { Text("Sélectionner contact") },
                placeholder = {
                    Text("Sélectionner contact")
                },

                leadingIcon = {
                    Spacer(modifier = Modifier.width(60.dp))
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },

                trailingIcon = {
                    IconButton(onClick = {
                        expanded = false
                    }) {

                        Icon(
                            Icons.Default.Add,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(80.dp))
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.primary
                ),
                //colors = ExposedDropdownMenuDefaults.textFieldColors(),
                shape = RoundedCornerShape(50)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UI2ComboContact(
    modifier: Modifier = Modifier.padding(6.dp),
    viewModelAdd: ViewModelAdd,
    navController: NavController,
    options: List<String>
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by viewModelAdd.textContactName
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(50),
            )

    ) {
        Row(modifier.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = ""
                )
            }
            Text(
                modifier = modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.White),

                text = selectedOptionText,
                color = MaterialTheme.colors.primary
            )
            IconButton(onClick = {
                expanded = false
            }) {
                Icon(
                    Icons.Default.Add,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = ""
                )
            }
            DropdownMenu(
                modifier=modifier
                    .fillMaxWidth(0.80f),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(
                            //modifier = modifier.fillMaxWidth(),
                            text = selectionOption,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}

