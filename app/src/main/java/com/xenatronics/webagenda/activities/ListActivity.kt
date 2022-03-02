package com.xenatronics.webagenda

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xenatronics.webagenda.activities.convertTime
import com.xenatronics.webagenda.components.NewTaskBar
import com.xenatronics.webagenda.viewmodel.ViewmodelRdv

@Composable
fun ListMain(viewModel: ViewmodelRdv) {
    Scaffold(
        topBar = {
            NewTaskBar(NavigateToListScreen = {})
        },
        content = {
            ListScreen(viewModel = viewModel) }
    )
}

@Composable
fun ListScreen(viewModel: ViewmodelRdv) {
    val liste = viewModel.allRdvFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Spacer(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp))
        LazyColumn(Modifier.fillMaxSize()) {
            if (liste.value.isEmpty())
                Log.d("Agenda", "Liste vide")
            liste.value.let { list ->
                //Log.d("Agenda", list.size.toString())
                items(list) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 5.dp, 10.dp, 5.dp),
                        elevation = 8.dp,
                        shape = RoundedCornerShape(10.dp)

                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            Row(Modifier
                                .fillMaxWidth()
                                .clickable {

                                }
                                .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = it.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                val date = convertTime(it.date.toLong())
                                Text(text = date, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
