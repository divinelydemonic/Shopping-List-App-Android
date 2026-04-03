package kr.android.shoppinglistapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    //for live-location
    private val _location = mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    // stores the last location manually picked by the user
    private val _lastSelectedLocation = mutableStateOf<LocationData?>(null)
    val lastSelectedLocation: State<LocationData?> = _lastSelectedLocation

    //for address
    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address : State<List<GeocodingResult>> = _address


    //function to update location
    fun updateLocation(newLocation : LocationData) {
        _location.value = newLocation
    }

    //save the manually picked location
    fun saveManualLocation(selection: LocationData) {
        _lastSelectedLocation.value = selection
    }

    //function to fetch human-readable address from the location
    fun fetchAddresses (latlng : String) {
        try {
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    BuildConfig.LOCATION_API_KEY
                )
                _address.value = result.results
            }
        } catch (e : Exception) {
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }

}