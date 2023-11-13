package com.example.dbapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dbapplication.data.NameEntity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InputArea()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputArea(
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModel.factory
    )
) {
    val itemsList = mainViewModel.itemsList
        .collectAsState(initial = emptyList())

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(modifier = Modifier.weight(1f),
                    value = mainViewModel.inputState.value,
                    onValueChange = {
                        mainViewModel.inputState.value = it
                    },
                    placeholder = {
                        Text(text = "Введите текст")
                    }
                )
                IconButton(
                    onClick = {
                        mainViewModel.insertItem()
                    }) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавить"
                    )
                }
            }
            OutputArea(itemsList, mainViewModel)
        }
    }
}

@Composable
fun OutputArea(list: State<List<NameEntity>>, mainViewModel: MainViewModel) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(list.value) { item ->
            ItemCard(item, {
                mainViewModel.textEntity = it
                mainViewModel.inputState.value = it.text
            },
                {
                    mainViewModel.deleteItem(it)
                }
            )
        }
    }
}

@Composable
fun ItemCard(
    item: NameEntity,
    onClick: (NameEntity) -> Unit,
    onClickDelete: (NameEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clickable {
                onClick(item)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(modifier = Modifier.padding(start = 10.dp)) {
                    Text(text = item.text)
                }
            }
            Column {
                Row {
                    IconButton(onClick = {
                        onClickDelete(item)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить"
                        )
                    }
                }
            }
        }
    }
}