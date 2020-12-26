package id.ac.ui.cs.mobileprogramming.hira.youngsil.api
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.WeatherResponse
import retrofit2.Response

interface IApiHelper {
    suspend fun getWeather(lat: Double, lon: Double): Response<WeatherResponse>
}