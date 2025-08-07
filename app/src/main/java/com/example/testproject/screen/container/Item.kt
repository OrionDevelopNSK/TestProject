package com.example.testproject.screen.container

import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.testproject.R
import com.example.testproject.model.Root
import com.example.testproject.viewmodel.BaseViewModel


@Composable
fun Item(root: Root, index: Int, viewModel: BaseViewModel, onNavigateToDetailScreen: () -> Unit) {
    val result = root.results[0]
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                viewModel.setIndexUser(index)
                onNavigateToDetailScreen.invoke()
            }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(result.picture.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.no_internet),
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp),
            onError = {
                Toast.makeText(
                    context,
                    getString(context, R.string.access_denied),
                    Toast.LENGTH_SHORT
                ).show()
            },
            placeholder = painterResource(id = R.drawable.placeholder)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(" ${result.name.title} ${result.name.first} ${result.name.last}")
            Text("${result.location.state}, ${result.location.city}, ${result.location.street.name}, ${result.location.street.number}")
            Text("tel.: ${result.phone}")
        }
    }

}