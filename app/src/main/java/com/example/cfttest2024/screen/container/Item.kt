package com.example.cfttest2024.screen.container

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cfttest2024.model.Root
import com.example.cfttest2024.viewmodel.BaseViewModel


@Composable
fun Item(root: Root, index: Int, viewModel: BaseViewModel, onNavigateToDetailScreen: () -> Unit) {
    val result = root.results[0]

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(8.dp)
            .clickable {
                viewModel.setIndexUser(index)
                onNavigateToDetailScreen.invoke()
            }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(result.picture.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(" ${result.name.title} ${result.name.first} ${result.name.last}")
            Text("${result.location.state}, ${result.location.city}, ${result.location.street.name}, ${result.location.street.number}")
            Text("tel.: ${result.phone}")
        }
    }

}