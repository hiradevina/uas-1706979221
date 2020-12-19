package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.WeatherDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Weather
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherDao: WeatherDao) :
    IWeatherRepository {
    override suspend fun insertWeather(lat: Double, lon: Double, temp: Double, location: String) {
        weatherDao.insertWeather(Weather(UUID.randomUUID().toString(), lat, lon, temp, location))
    }
}