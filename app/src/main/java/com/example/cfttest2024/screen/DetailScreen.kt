package com.example.cfttest2024.screen

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cfttest2024.viewmodel.BaseViewModel

@Composable
fun DetailScreen(viewModel: BaseViewModel, onNavigateToMainScreen: () -> Unit) {

    val root = viewModel.currentIndexUser.value?.let { viewModel.rootData.value?.get(it) } ?: return
    val result = root.results[0]
    val scrollState = rememberScrollState()
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .weight(0.70f)
                .verticalScroll(scrollState)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(result.picture.large)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)) {
                Text("Name: ")
                Text(" ${result.name.title} ${result.name.first} ${result.name.last}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(" Address",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold)

            Row {
                Text("Street: ",
                    fontWeight = FontWeight.Bold)
                Text(" ${result.location.street.name}")
            }

            Row {
                Text("Number of street: ",
                    fontWeight = FontWeight.Bold)
                Text("${result.location.street.number}")
            }

            Row {
                Text("City: ",
                    fontWeight = FontWeight.Bold)
                Text(result.location.city)
            }

            Row {
                Text("State: ",
                    fontWeight = FontWeight.Bold)
                Text(result.location.state)
            }

            Row {
                Text("Country: ",
                    fontWeight = FontWeight.Bold)
                Text(result.location.country)
            }

            Row {
                Text("Postcode: ",
                    fontWeight = FontWeight.Bold)
                Text(result.location.postcode)
            }

            Row {
                Text("Timezone: ",
                    fontWeight = FontWeight.Bold)
                Text("${result.location.timezone.description} (${result.location.timezone.offset})")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text("Coordinates: ",
                    fontWeight = FontWeight.Bold)
                Text("Latitude: ${result.location.coordinates.latitude}, longitude: ${result.location.coordinates.longitude}",
                    modifier = Modifier.clickable {viewModel.openMap(context) },
                    style = TextStyle(fontStyle = FontStyle.Italic))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text("Tel.: ",
                    fontWeight = FontWeight.Bold)
                Text(result.phone, modifier = Modifier.clickable {viewModel.callPhone(context) },
                    style = TextStyle(fontStyle = FontStyle.Italic))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text("Email: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    result.email,
                    modifier = Modifier.clickable { viewModel.openEmailProgram(context) },
                    style = TextStyle(fontStyle = FontStyle.Italic))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(" Login", modifier = Modifier
                .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold)

            Row {
                Text("UUID: ",
                    fontWeight = FontWeight.Bold)
                Text(result.login.uuid)
            }

            Row {
                Text("Username: ",
                    fontWeight = FontWeight.Bold)
                Text(result.login.username)
            }

            Row {
                Text("Password: ",
                    fontWeight = FontWeight.Bold)
                Text(result.login.password)
            }

            Row {
                Text("Salt: ",
                    fontWeight = FontWeight.Bold)
                Text(result.login.salt)
            }

            Row {
                Text("Md5: ",
                    fontWeight = FontWeight.Bold)
                Text(result.login.md5)
            }

            Row {
                Text("Sha1: ",
                    fontWeight = FontWeight.Bold)
                Text(result.login.sha1)
            }

            Row {
                Text("Sha256: ",
                    fontWeight = FontWeight.Bold)
                Text(result.login.sha256)
            }

            Row {
                Text("Day of birthday: ",
                    fontWeight = FontWeight.Bold)
                Text("${result.dob.date} (${result.dob.age} years)")
            }

            Row {
                Text("Day of registration: ",
                    fontWeight = FontWeight.Bold)
                Text("${result.registered.date} (${result.registered.age} years)")
            }

            Row {
                Text("Cell: ",
                    fontWeight = FontWeight.Bold)
                Text(result.cell)
            }

            Row {
                Text("Id: ",
                    fontWeight = FontWeight.Bold)
                Text("${result.id.name},  (${result.id.value}")
            }

            Row {
                Text("Nationality: ",
                    fontWeight = FontWeight.Bold)
                Text(result.nat)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(" Info",
                fontWeight = FontWeight.Bold)

            Row {
                Text("Seed: ",
                    fontWeight = FontWeight.Bold)
                Text(root.info.seed)
            }

            Row {
                Text("Results: ",
                    fontWeight = FontWeight.Bold)
                Text("${root.info.results}")
            }

            Row {
                Text("Page: ",
                    fontWeight = FontWeight.Bold)
                Text("${root.info.page}")
            }

            Row {
                Text("Page: ",
                    fontWeight = FontWeight.Bold)
                Text(root.info.version)
            }

        }

        Button(onClick = { onNavigateToMainScreen.invoke() },
            modifier = Modifier.fillMaxWidth().height(40.dp)) {
            Text("Back")
        }
    }

}