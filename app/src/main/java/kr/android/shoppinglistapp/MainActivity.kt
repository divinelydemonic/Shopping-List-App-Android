package kr.android.shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kr.android.shoppinglistapp.ui.theme.ScreenBg
import kr.android.shoppinglistapp.ui.theme.ShoppingListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel : LocationViewModel = viewModel()
            val navController = rememberNavController()
            val context = LocalContext.current
            val locationUtils = LocationUtils(context)

            ShoppingListAppTheme {
                Scaffold(modifier = Modifier
                    .background(ScreenBg)
                    .fillMaxSize()) { innerPadding ->
                    LocationPermissionHandler(
                        viewModel = viewModel,
                        locationUtils = locationUtils,
                        navController = navController
                    ) {
                        Navigation(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = viewModel,
                            navController = navController,
                            context = context,
                            locationUtils = locationUtils
                        )
                    }
                }
            }
        }
    }
}

