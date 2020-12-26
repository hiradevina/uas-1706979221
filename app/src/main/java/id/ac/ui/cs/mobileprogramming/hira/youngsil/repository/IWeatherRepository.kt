package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.WeatherResponse
import retrofit2.Response

interface IWeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double) : Response<WeatherResponse>
    suspend fun insertWeather(lat: Double, lon: Double, temp: Double, location: String)
}