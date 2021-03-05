package com.example.my_weather.ui.main.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.my_weather.R
import com.example.my_weather.data.local.DatabaseApp
import com.example.my_weather.data.local.model.Favorite
import com.example.my_weather.data.remote.RetrofitManager
import com.example.my_weather.data.remote.model.ForecastResult
import com.example.my_weather.databinding.ActivityForecastBinding
import com.example.my_weather.extension.isInternetAvailable
import com.example.my_weather.extension.toPx
import com.example.my_weather.ui.main.model.CityRaw
import com.example.my_weather.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding

    private val searchAdapter by lazy { ForecastAdapter(this) }

    private lateinit var cityRaw: CityRaw

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<CityRaw>("cityRaw")?.let {
            cityRaw = it
        }
        initUi()
        updateToggleButton(checkFavorite() == null)
        listForecasts(cityRaw.id)
    }

    private fun initUi() {
        binding.apply {
            tvForecastTitle.text = this@ForecastActivity.resources.getString(
                R.string.forecast_title, cityRaw.name, cityRaw.country)
            ivForecastWeather.load(
                IconUtils.getWeatherIconUrl(cityRaw.tempIcon)) {
                crossfade(true)
                placeholder(R.drawable.ic_weather_placeholder
            )}
            tvForecastTempAmount.text = cityRaw.tempAmount.toString()
            tvForecastTempUnit.text = cityRaw.tempUnit

            btnFavorite.setOnClickListener {
                toggleFavorite()
            }

            rvForecasts.apply {
                layoutManager = LinearLayoutManager(this@ForecastActivity)
                adapter = searchAdapter
                addItemDecoration(MarginItemDecoration(16.toPx()))
            }
        }
    }

    private fun checkFavorite(): Favorite? {
        val dao = DatabaseApp.getInstance(this@ForecastActivity).getFavoriteDao()
        return dao.getById(cityRaw.id)
    }

    private fun toggleFavorite() {
        cityRaw.let {
            val dao = DatabaseApp.getInstance(this@ForecastActivity).getFavoriteDao()
            val cityFavorited = dao.getById(cityRaw.id)

            if (cityFavorited == null) {
                dao.insert(Favorite(it.id, it.name, it.country))
                updateToggleButton(false)
                Toast.makeText(this@ForecastActivity, this.resources.getString(R.string.text_favorited), Toast.LENGTH_SHORT).show()
            } else {
                dao.delete(cityFavorited)
                updateToggleButton(true)
                Toast.makeText(this@ForecastActivity, this.resources.getString(R.string.text_un_favorited), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateToggleButton(isFavorite: Boolean) {
        binding.btnFavorite.text = if (isFavorite) {
            this.resources.getString(R.string.text_favorite)
        } else {
            this.resources.getString(R.string.text_un_favorite)
        }
    }

    private fun listForecasts(cityId: Long) {
        if (this.isInternetAvailable()) {
            val call = RetrofitManager.getOpenForecastService().listForecasts(
                cityId,
                SharedPrefsUtils.getUnitKey(this@ForecastActivity),
                SharedPrefsUtils.getLangKey(this@ForecastActivity),
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