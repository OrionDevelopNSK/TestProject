package com.example.cfttest2024.screen

import android.app.Activity
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
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cfttest2024.model.Root
import com.example.cfttest2024.screen.container.Item
import com.example.cfttest2024.viewmodel.BaseViewModel

@Composable
fun MainScreen(viewModel: BaseViewModel, onNavigateToDetailScreen: () -> Unit) {

    val listState = rememberLazyListState()
    val infos = viewModel.rootData.observeAsState(mutableListOf())
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {


        LazyColumn(
            state = listState,
            modifier = Modifier.weight(0.70f)

        ) {

            itemsIndexed(items = infos.value) { index, item: Root ->
                Item(
                    root = item,
                    index = index,
                    viewModel = viewModel,
                    onNavigateToDetailScreen = onNavigateToDetailScreen
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.downloadResults(context)
                println(infos.value.size)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(8.dp)
        ) {
            Text("Load user")
        }

        Button(
            onClick = { viewModel.clearDatabase(context) },
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(8.dp)
        ) {
            Text("Clear users list")
        }

    }

}


