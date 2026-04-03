package kr.android.shoppinglistapp

//stores the location latitude and longitude
data class LocationData(
    val latitude : Double,
    val longitude : Double
)

//stores the human-readable address after reversing the geocode
data class GeocodingResult(
    val formatted_address : String
)

//stores the response from the API (list of results and status of the request)
data class GeocodingResponse(
    val results : List<GeocodingResult> ,
    val status : String
)
