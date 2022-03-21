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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.util.Constants.HEIGHT_COMPONENT
import com.xenatronics.webagenda.util.Constants.RADIUS_MEDIUM
import com.xenatronics.webagenda.viewmodel.ViewModelRdv


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UI2ComboContact(
    modifier: Modifier = Modifier.padding(6.dp),
    viewModel: ViewModelRdv,
    navController: NavController,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by viewModel.nom
    Box(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
            .height(HEIGHT_COMPONENT)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(RADIUS_MEDIUM),
            ),

        ) {
        Row(
            modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
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
                navController.navigate(Screen.NewContactScreen.route)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_add),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = ""
                )
            }
            DropdownMenu(
                modifier = Modifier
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

