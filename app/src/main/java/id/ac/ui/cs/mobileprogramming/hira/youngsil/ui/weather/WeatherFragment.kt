package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.weather


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.hira.youngsil.R
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.State
import kotlinx.android.synthetic.main.weather_fragment.*


@AndroidEntryPoint
class WeatherFragment : Fragment() {

    companion object {
        private const val TAG = "WeatherFragment"
    }

    private val weatherViewModel: WeatherViewModel by activityViewModels() {
        defaultViewModelProviderFactory
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestMultiplePermissions =
        this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (!permissions.containsValue(false)) {
                getCurrentLocation()
            }
        }

    private fun checkLocationPermission(
        context: Context,
    ) {
        when {
            ((PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED) || (PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED)
                    ) -> {
                getCurrentLocation()
            }
            shouldShowRequestPermissionRationale("To get weather info") -> {

            }
            else -> {
                val locationPermission =
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                requestMultiplePermissions.launch(
                    locationPermission
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    weatherViewModel.fetchWeatherInfo(location.latitude, location.longitude)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireView().context)
        weatherViewModel.connectedToNetwork.observe(viewLifecycleOwner) {
            if (it) {
                checkLocationPermission(view.context)
            } else {
                weather_warning.text = getString(R.string.weather_warning_no_connection)
            }
        }

        val connectivityManager =
            view.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        weatherViewModel.updateNetwork(!connectivityManager.isActiveNetworkMetered)

        weatherViewModel.weatherResponse.observe(viewLifecycleOwner) {
            when (it) {
                is State.Success -> {
                    hideLoading()
                    if (it.data != null) {
                        weather_warning.text = ""
                        weather_location.text = getString(R.string.weather_location, it.data.name)
                        weather_temperature.text =
                            getString(R.string.weather_temperature, it.data.main.temp)
                    }
                }
                is State.Loading -> showLoading()

                is State.Failed -> {
                    Toast.makeText(context, getString(R.string.weather_failed), Toast.LENGTH_LONG)
                        .show()
                }

                is State.Initialize -> hideLoading()
            }
        }
    }

    fun hideLoading() {
        weather_loading.visibility = View.GONE
    }

    fun showLoading() {
        weather_loading.visibility = View.VISIBLE
    }

}