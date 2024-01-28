package com.example.cfttest2024.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cfttest2024.model.Root

@Composable
fun DetailScreen(root: Root) {

    val result = root.results[0]

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
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
                .size(100.dp)
        )

        Row {
            Text("Name: ")
            Text(" ${result.name.title} ${result.name.first} ${result.name.last}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(" Address")

        Row {
            Text("Street: ")
            Text(" ${result.location.street.name}")
        }

        Row {
            Text("Number of street: ")
            Text("${result.location.street.number}")
        }

        Row {
            Text("City: ")
            Text("${result.location.city}")
        }

        Row {
            Text("State: ")
            Text("${result.location.state}")
        }

        Row {
            Text("State: ")
            Text("${result.location.state}")
        }

        Row {
            Text("Country: ")
            Text("${result.location.country}")
        }

        Row {
            Text("Postcode: ")
            Text("${result.location.postcode}")
        }

        Row {
            Text("Postcode: ")
            Text("${result.location.postcode}")
        }

        Row {
            Text("Coordinates: ")
            Text("Latitude: ${result.location.coordinates.latitude}, longitude: ${result.location.coordinates.longitude}",
                modifier = Modifier.clickable { })
        }

        Row {
            Text("Timezone: ")
            Text("${result.location.timezone.description} (${result.location.timezone.offset})")
        }

        Row {
            Text("Email: ")
            Text("${result.email}",
                modifier = Modifier.clickable { })
        }

        Text(" Login")

        Row {
            Text("UUID: ")
            Text("${result.login.uuid}")
        }

        Row {
            Text("Username: ")
            Text("${result.login.username}")
        }

        Row {
            Text("Password: ")
            Text("${result.login.password}")
        }

        Row {
            Text("Salt: ")
            Text("${result.login.salt}")
        }

        Row {
            Text("Md5: ")
            Text("${result.login.md5}")
        }

        Row {
            Text("Md5: ")
            Text("${result.login.md5}")
        }

        Row {
            Text("Sha1: ")
            Text("${result.login.sha1}")
        }

        Row {
            Text("Sha256: ")
            Text("${result.login.sha256}")
        }

        Row {
            Text("Day of birthday: ")
            Text("${result.dob.date} (${result.dob.age} years)")
        }

        Row {
            Text("Day of registration: ")
            Text("${result.registered.date} (${result.registered.age} years)")
        }

        Row {
            Text("Tel.: ")
            Text("${result.phone}", modifier = Modifier.clickable { })
        }

        Row {
            Text("Cell: ")
            Text("${result.cell}")
        }

        Row {
            Text("Id: ")
            Text("${result.id.name},  (${result.id.value}")
        }

        Row {
            Text("Nationality: ")
            Text("${result.nat}")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(" Info")

        Row {
            Text("Seed: ")
            Text("${root.info.seed}")
        }

        Row {
            Text("Results: ")
            Text("${root.info.results}")
        }

        Row {
            Text("Page: ")
            Text("${root.info.page}")
        }

        Row {
            Text("Page: ")
            Text("${root.info.version}")
        }









    }

}