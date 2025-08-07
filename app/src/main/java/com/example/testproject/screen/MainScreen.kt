package com.example.testproject.screen

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.testproject.model.Root
import com.example.testproject.screen.container.Item
import com.example.testproject.viewmodel.BaseViewModel

@Composable
fun MainScreen(viewModel: BaseViewModel, onNavigateToDetailScreen: () -> Unit) {

    val listState = rememberLazyListState()
    val infos = viewModel.rootData.observeAsState(emptyList())
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val errorResId by viewModel.errorEvent.observeAsState(null)



    LaunchedEffect(errorResId) {
        if (errorResId != null) {
            snackbarHostState.showSnackbar(
                message = context.getString(errorResId!!),
                duration = SnackbarDuration.Short
            )
            viewModel.resetError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

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
                    viewModel.downloadResults()
                    println(infos.value.size)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp)
            ) {
                Text("Load user")
            }

            Button(
                onClick = { viewModel.clearDatabase() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp)
            ) {
                Text("Clear users list")
            }
        }
    }
}


