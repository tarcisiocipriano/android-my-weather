package com.example.my_weather.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_weather.R
import com.example.my_weather.data.remote.model.City
import com.example.my_weather.databinding.ItemCityBinding
import com.example.my_weather.util.SharedPrefsUtils

class SearchAdapter(): ListAdapter<City, SearchAdapter.ViewHolder>(SearchDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            val imgUrl = "http://openweathermap.org/img/wn/${city.weathers[0].icon}@4x.png"

            binding.tvCityName.text = city.name
            binding.tvCountryName.text = city.country.name
            binding.tvWeatherCondition.text = city.weathers[0].description
            binding.ivWeather.load(imgUrl) {
                  crossfade(true)
                  placeholder(R.drawable.ic_weather_placeholder)
            }
            binding.tvTemperatureAmount.text = city.main.temperature
            binding.tvTemperatureUnit.text = when (SharedPrefsUtils.getTempUnitSearched()) {
                "metric" -> "C°"
                "imperial" -> "F°"
                else -> ""
            }
            binding.tvCloudPercentage.text = city.clouds.percentage.toString()
            binding.tvWindSpeed.text = city.wind.speed.toString()
        }
    }

    class SearchDiff: DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem == newItem

        override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem
    }


}