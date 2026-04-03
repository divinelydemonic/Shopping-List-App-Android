package kr.android.shoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kr.android.shoppinglistapp.ui.theme.PrimaryButton

@Composable
fun LocationSelectionDialog (
    location : LocationData,
    onLocationSelected : (LocationData) -> Unit
) {

    //for storing user location in form of lat & long
    val userLocation = remember {
        mutableStateOf(LatLng(
            location.latitude,
            location.longitude)
        )
    }

    //for storing exact position of location in the map
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            userLocation.value,     //where to zoom (user location)
            17f     //how much to zoom
        )
    }

    // Define the properties for the map
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(isMyLocationEnabled = true) // Shows the blue dot
        )
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(myLocationButtonEnabled = true) // Shows the "target" button
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier
                .height(700.dp),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color.Black
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 6.dp
            )
        ) {

            GoogleMap(
                properties = mapProperties, // Apply properties here
                uiSettings = uiSettings,     // Apply UI settings here
                cameraPositionState = cameraPositionState,
                onMapClick = {
                    userLocation.value = it
                },
            ) {
                Marker(
                    state = MarkerState(position = userLocation.value),
                    draggable = true
                )
            }

        }

        Spacer(Modifier.height(16.dp))

        //new selected location
        var newLocation : LocationData

        Button(
            onClick = {
                //overrides the starting location with the newly selected location
                newLocation = LocationData(
                    userLocation.value.latitude,
                    userLocation.value.longitude
                )
                //passes the new location from the map
                onLocationSelected(newLocation)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryButton
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(
                "Update Location",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }

}