package com.example.my_weather.ui.main.forecast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_weather.R
import com.example.my_weather.data.remote.model.Forecast
import com.example.my_weather.databinding.ItemForecastCityBinding
import com.example.my_weather.util.DateTimePresenter
import com.example.my_weather.util.SharedPrefsUtils

class ForecastAdapter: ListAdapter<Forecast, ForecastAdapter.ViewHolder>(SearchDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemForecastCityBinding): RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("SimpleDateFormat")
        fun bind(forecast: Forecast) {
            val imgUrl = "http://openweathermap.org/img/wn/${forecast.weathers[0].icon}@4x.png"
            val dateTime = DateTimePresenter(forecast.dateTime)

            binding.apply {
                tvForecastDate.text = dateTime.present()
                ivWeather.load(imgUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_weather_placeholder)
                }
                tvWeatherCondition.text = forecast.weathers[0].description
                tvTemperatureAmount.text = forecast.main.temperature
                tvTemperatureUnit.text = when (SharedPrefsUtils.getTempUnitSearched()) {
                    "metric" -> "C°"
                    "imperial" -> "F°"
                    else -> "C°"
                }
                tvCloudPercentage.text = forecast.clouds.percentage.toString()
                tvWindSpeed.text = forecast.wind.speed.toString()
            }
        }
    }

    class SearchDiff: DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast) = oldItem == newItem
    }


}