package com.example.testproject.screen

import android.content.ActivityNotFoundException
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.testproject.R
import com.example.testproject.util.Converter
import com.example.testproject.logic.viewmodel.MainViewModel

@Composable
fun DetailScreen(viewModel: MainViewModel, onNavigateToMainScreen: () -> Unit) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val currentIndex by viewModel.currentIndexUser.collectAsState()
    val rootData by viewModel.rootData.collectAsState()
    val root = rootData.getOrNull(currentIndex) ?: return
    val result = root.results[0]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier
                .weight(0.70f)
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {

            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(result.picture.large)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.no_internet),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally),
                    onError = {
                        Toast.makeText(
                            context,
                            ContextCompat.getString(context, R.string.access_denied),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    placeholder = painterResource(id = R.drawable.placeholder)
                )

                Text(
                    " ${result.name.title} ${result.name.first} ${result.name.last}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.phone),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Red
                    )
                    Text(
                        result.phone,
                        modifier = Modifier.clickable {
                            try {
                                viewModel.callPhone()
                            } catch (_: ActivityNotFoundException) {
                                Toast.makeText(
                                    context,
                                    "No phone app available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (_: SecurityException) {
                                Toast.makeText(
                                    context,
                                    "No access to calls",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.email),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Red
                    )
                    Text(
                        result.email,
                        modifier = Modifier.clickable { viewModel.openEmailProgram() },
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.loc),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Red
                    )
                    Text(
                        "Latitude: ${result.location.coordinates.latitude}, longitude: ${result.location.coordinates.longitude}",
                        modifier = Modifier.clickable {
                            try {
                                viewModel.openMap()
                            } catch (_: ActivityNotFoundException) {
                                Toast.makeText(
                                    context,
                                    "No map app installed!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    " Address",
                    modifier = Modifier
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Street: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(" ${result.location.street.name}")
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Number of street: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${result.location.street.number}")
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "City: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.location.city)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "State: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.location.state)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Country: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.location.country)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Postcode: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.location.postcode)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Timezone: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${result.location.timezone.description} (${result.location.timezone.offset})")
                }

                Spacer(modifier = Modifier.height(8.dp))

            }


            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    " Login", modifier = Modifier
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "UUID: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.login.uuid)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Username: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.login.username)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Password: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.login.password)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Salt: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.login.salt)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Md5: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.login.md5)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Sha1: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.login.sha1)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Sha256: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        result.login.sha256, modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Day of birthday: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${Converter.isoToDate(result.dob.date)} (${result.dob.age} years)")
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Day of registration: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${Converter.isoToDate(result.registered.date)} (${result.registered.age} years)")
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Cell: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.cell)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Id: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${result.id.name},  ${result.id.value}")
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Nationality: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(result.nat)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }


            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    " Info",
                    modifier = Modifier
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Seed: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(root.info.seed)
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Results: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${root.info.results}")
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Page: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${root.info.page}")
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "Page: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(root.info.version)
                }

                Spacer(modifier = Modifier.height(8.dp))

            }

        }

        Button(
            onClick = { onNavigateToMainScreen.invoke() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(8.dp)
        ) {
            Text("Back")
        }
    }

}