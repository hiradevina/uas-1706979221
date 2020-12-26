package id.ac.ui.cs.mobileprogramming.hira.youngsil.entity

data class Coordinate(val lon: Double?, val lat: Double?)

data class WeatherInfo(val main: String?, val description: String?, val icon: String?)

data class WeatherDetail(
    val temp: Double?,
    val feelsLike: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val pressure: Double?,
    val humidity: Double?
)

data class Wind(val speed: Double?, val deg: Double?)
data class Clouds(val all: Double?)
data class WeatherResponse(
    val coord: Coordinate,
    val weather: List<WeatherInfo?>,
    val base: String?,
    val main: WeatherDetail,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long?,
    val timezone: Int?,
    val id: Long?,
    val name: String?
)