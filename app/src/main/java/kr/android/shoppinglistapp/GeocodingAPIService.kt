package kr.android.shoppinglistapp

import retrofit2.http.GET
import retrofit2.http.Query

//interface for creating the endpoint of API
interface GeocodingAPIService {
    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latlng : String,
        @Query("key") key : String
    ) : GeocodingResponse
}