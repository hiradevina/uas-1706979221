package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

import id.ac.ui.cs.mobileprogramming.hira.youngsil.api.ApiHelper
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.WeatherDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Weather
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.WeatherResponse
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val apiHelper: ApiHelper
) :
    IWeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Response<WeatherResponse> =
        apiHelper.getWeather(lat, lon)

    override suspend fun insertWeather(lat: Double, lon: Double, temp: Double, location: String) {
        weatherDao.insertWeather(Weather(UUID.randomUUID().toString(), lat, lon, temp, location))
    }
}