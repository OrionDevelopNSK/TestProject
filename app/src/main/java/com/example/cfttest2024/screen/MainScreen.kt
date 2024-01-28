package com.example.cfttest2024.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cfttest2024.model.Root
import com.example.cfttest2024.screen.container.Item
import com.example.cfttest2024.viewmodel.BaseViewModel

@Composable
fun MainScreen(viewModel: BaseViewModel) {

    val listState = rememberLazyListState()
    val infos = viewModel.rootData.observeAsState(mutableListOf())

    Column(modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 8.dp)
        .fillMaxWidth().fillMaxHeight()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(0.75f)

        ) {
            itemsIndexed(items = infos.value) { _, item: Root ->
                Item(result = item.results[0])
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.downloadResults() },
            modifier = Modifier.fillMaxWidth().height(40.dp)) {
            Text("Load user")
        }

    }


}


