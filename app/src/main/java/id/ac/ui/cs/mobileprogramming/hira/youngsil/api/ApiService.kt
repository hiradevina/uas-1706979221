package id.ac.ui.cs.mobileprogramming.hira.youngsil.api

import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>
}