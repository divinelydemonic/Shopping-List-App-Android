package kr.android.shoppinglistapp

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import kr.android.shoppinglistapp.ui.theme.EditBlue

@Composable
fun Navigation(
    modifier: Modifier,
    viewModel: LocationViewModel,
    navController: NavHostController,
    context: Context,
    locationUtils: LocationUtils
) {

    NavHost(
        navController = navController,
        startDestination = "shoppingListScreen"
    ) {
        composable(
            route = "shoppingListScreen"
        ) {
            ShoppingList(
                viewModel,
                navController,
                context,
                locationUtils
            )
        }

        dialog(
            route = "locationDialog"
        ) {

            // Check if we have a saved pick; if not, use live GPS
            val startLocation = viewModel.lastSelectedLocation.value
                ?: viewModel.location.value

            if (startLocation != null) {
                LocationSelectionDialog(
                    location = startLocation,
                    onLocationSelected = { locationData ->
                        // Save the choice so it stays there next time the map opens!
                        viewModel.saveManualLocation(locationData)

                        viewModel.fetchAddresses("${locationData.latitude}, ${locationData.longitude}")
                        navController.popBackStack()
                    }
                )
            } else {
                //loading map
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = EditBlue,
                        strokeCap = StrokeCap.Round,
                        trackColor = Color.White,
                        strokeWidth = 8.dp
                    )
                }
            }
        }
    }
}