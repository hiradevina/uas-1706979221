package id.ac.ui.cs.mobileprogramming.hira.youngsil.api

import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) : IApiHelper {
    override suspend fun getWeather(lat: Double, lon: Double): Response<WeatherResponse> =
        apiService.getWeather(lat, lon, "8ff2ad59864e1cb27c042fe9d0d75cb3")
}