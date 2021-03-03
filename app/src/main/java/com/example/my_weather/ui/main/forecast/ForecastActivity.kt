package com.example.my_weather.ui.main.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.my_weather.R
import com.example.my_weather.data.remote.RetrofitManager
import com.example.my_weather.data.remote.model.ForecastResult
import com.example.my_weather.databinding.ActivityForecastBinding
import com.example.my_weather.extension.isInternetAvailable
import com.example.my_weather.extension.toPx
import com.example.my_weather.util.FileUtils
import com.example.my_weather.util.MarginItemDecoration
import com.example.my_weather.util.SharedPrefsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding

    private val searchAdapter by lazy { ForecastAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        listForecasts(intent.getLongExtra("cityId", 0))
    }

    private fun initUi() {
        binding.apply {
            val title = "Weather in ${intent.getStringExtra("cityName")}, ${intent.getStringExtra("cityCountry")} "
            val imgUrl = "http://openweathermap.org/img/wn/${intent.getStringExtra("cityTempIcon")}@4x.png"

            tvForecastTitle.text = title
            ivForecastWeather.load(imgUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_weather_placeholder)
            }
            tvForecastTemperatureAmount.text = intent.getStringExtra("cityTempAmount")
            tvForecastTemperatureUnit.text = when (intent.getStringExtra("cityTempUnit")) {
                "metric" -> "C°"
                "imperial" -> "F°"
                else -> "C°"
            }
        }

        binding.rvForecasts.apply {
            layoutManager = LinearLayoutManager(this@ForecastActivity)
            adapter = searchAdapter
            addItemDecoration(MarginItemDecoration(16.toPx()))
        }
    }

    private fun listForecasts(cityId: Long) {
        if (this.isInternetAvailable()) {
            val call = RetrofitManager.getOpenForecastService().listForecasts(
                cityId,
                SharedPrefsUtils.getUnitKey(this@ForecastActivity, SharedPrefsUtils.UNIT_KEY),
                SharedPrefsUtils.getLangKey(this@ForecastActivity, SharedPrefsUtils.LANG_KEY),
                FileUtils.readEncrypted(
                    this@ForecastActivity,
                    File(this@ForecastActivity.filesDir, "secretApiKey")
                )
            )
            call.enqueue(object : Callback<ForecastResult> {
                override fun onResponse(call: Call<ForecastResult>, response: Response<ForecastResult>) {
                    if (response.isSuccessful) {
                        searchAdapter.submitList(response.body()?.forecast)
                    } else {
                        Log.w("TAG", "onResponse: ${response.message()} ")
                    }
                }

                override fun onFailure(call: Call<ForecastResult>, t: Throwable) {
                    Log.e("TAG", "onFailure: ", t)
                }
            })
        } else {
            Toast.makeText(this, "No network access", Toast.LENGTH_SHORT).show()
        }
    }
}