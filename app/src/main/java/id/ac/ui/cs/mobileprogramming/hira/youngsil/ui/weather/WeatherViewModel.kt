package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.WeatherResponse
import id.ac.ui.cs.mobileprogramming.hira.youngsil.repository.WeatherRepository
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    companion object {
        private const val TAG = "WeatherViewModel"
    }

    private val _weatherResponse = MutableLiveData<State<WeatherResponse>>()
    val weatherResponse: LiveData<State<WeatherResponse>>
        get() = _weatherResponse

    private val _isConnectedToNetwork = MutableLiveData<Boolean>()
    val connectedToNetwork: LiveData<Boolean>
        get() = _isConnectedToNetwork

    private var fetchWeatherJob: Job? = null

    init {
        // init state
        _weatherResponse.postValue(State.init())
    }

    fun updateNetwork(isConnected: Boolean) {
        _isConnectedToNetwork.postValue(isConnected)
    }

    fun fetchWeatherInfo(lat: Double, lon: Double) {
        fetchWeatherJob?.cancel()
        fetchWeatherJob = viewModelScope.launch (Dispatchers.IO){
            _weatherResponse.postValue(State.loading())
            weatherRepository.getWeather(lat, lon).let { response ->
                if (response.isSuccessful) {
                    _weatherResponse.postValue(State.Success(response.body()))
                }
            }
        }
    }
}