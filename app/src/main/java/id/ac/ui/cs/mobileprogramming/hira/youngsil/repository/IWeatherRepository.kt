package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

interface IWeatherRepository {
    suspend fun insertWeather(lat: Double, lon: Double, temp: Double, location: String)
}