package com.example.my_weather.ui.main.forecast

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_weather.R
import com.example.my_weather.data.remote.model.Forecast
import com.example.my_weather.databinding.ItemCityForecastBinding
import com.example.my_weather.util.DateTimePresenter
import com.example.my_weather.util.IconUtils
import com.example.my_weather.util.SharedPrefsUtils

class ForecastAdapter(private val context: Context): ListAdapter<Forecast, ForecastAdapter.ViewHolder>(SearchDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCityForecastBinding): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(forecast: Forecast) {
            binding.apply {
                val lang = SharedPrefsUtils.getLangKey(context)
                tvForecastDate.text = DateTimePresenter.present(forecast.dateTime, lang)
                ivWeatherIcon.load(IconUtils.getWeatherIconUrl(forecast.weathers[0].icon)) {
                    crossfade(true)
                    placeholder(R.drawable.ic_weather_placeholder)
                }
                tvWeatherCondition.text = forecast.weathers[0].description
                tvTempAmount.text = forecast.main.temperature.toString()
                tvTempUnit.text = SharedPrefsUtils.getTempUnitSearched()
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