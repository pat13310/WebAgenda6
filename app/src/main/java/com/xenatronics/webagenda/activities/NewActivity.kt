package com.xenatronics.webagenda.activities

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import com.xenatronics.webagenda.Action
import com.xenatronics.webagenda.NewTaskBar
import com.xenatronics.webagenda.data.PostRequest
import com.xenatronics.webagenda.viewmodel.ViewmodelRdv

val TOP_SPACE = 6.dp

@Composable
fun NewMain(viewModel: ViewmodelRdv) {
    Scaffold(
        topBar = {
            NewTaskBar(NavigateToListScreen = {action->
                when (action){
                    Action.ADD->{
                        val post=PostRequest( viewModel.nom.value, viewModel.timestamp.value)
                        viewModel.AddRdv(post)
                    }
                    Action.NO_ACTION->{

                    }
                }
            })
        },
        content = { NewCardScreen(viewModel = viewModel) }
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NewCardScreen(viewModel: ViewmodelRdv) {
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

        TextNom(
            textNom = nom
        ) { nom = it }
        TextStandard(
            label="Adresse",
            textStandard = adresse,
            onTextChanged = { adresse = it }
        )
        TextStandard(
            label="Ville",
            textStandard = ville,
            onTextChanged = { ville = it }
        )
        TextCP(
            textCP = cp,
            onTextChanged = { cp = it }
        )
        TextMail(
            textMail = mail,
            onTextChanged = { mail = it }
        )
        TextPhone(
            textPhone = phone,
            onTextChanged = { phone = it }
        )
    }
}


@Composable
fun TextMail(
    textMail: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textMail,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "emailIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        label = { Text(text = "Email") },
        placeholder = { Text(text = "Adresse mail") },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}


@Composable
fun TextPhone(
    textPhone: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textPhone,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "phoneIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        label = { Text(text = "Téléphone") },
        placeholder = { Text(text = "Numéro de téléphone") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}

@Composable
fun TextCP(
    textCP: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textCP,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "CPIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        label = { Text(text = "Code postal") },
        placeholder = { Text(text = "Code postal") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}

@Composable
fun TextStandard(
    label:String="",
    textStandard: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textStandard,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "CPIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        label = { Text(text = label) },
        placeholder = { Text(text = label) },
        shape = RoundedCornerShape(12.dp),

        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}

@Composable
fun TextNom(
    textNom: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = textNom,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "CPIcon"
            )
        },
        onValueChange = {
            onTextChanged(it)
        },
        label = { Text(text = "Nom") },
        placeholder = { Text(text = "Nom du rendez-vous") },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_SPACE)
    )
}

