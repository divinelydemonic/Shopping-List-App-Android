package kr.android.shoppinglistapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//for joining the base URL with the endpoint
object RetrofitClient {

    private const val BASE_URL = "https://maps.googleapis.com/"

    fun create() : GeocodingAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GeocodingAPIService :: class.java)
    }

}