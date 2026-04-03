package kr.android.shoppinglistapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils (var context: Context) {

    private val _fusedLocationProviderClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    //requesting the location
    //suppress lint since we will be handling the permission ourselves
    @SuppressLint("MissingPermission")
    fun requestLocationUpdates (viewModel: LocationViewModel) {

        //how to request the location
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        ).build()

        //what to do with the requested location
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val location = LocationData(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                    viewModel.updateLocation(location)
                }
            }
        }

        //triggering the location request
        _fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    //converting coordinates to address (optional since we're using geocoding API)
    fun reverseGeocodeLocation (location : LocationData) : String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val coordinates = LatLng(
            location.latitude,
            location.longitude
        )
        val addresses : MutableList<Address>? = geocoder.getFromLocation(
            coordinates.latitude,
            coordinates.longitude,
            1
        )

        return if (addresses?.isNotEmpty() == true) {
            addresses[0].getAddressLine(0)
        } else {
            "No address found!!!"
        }
    }

    //checking if permission is available
    fun checkPermission (context: Context) : Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

}