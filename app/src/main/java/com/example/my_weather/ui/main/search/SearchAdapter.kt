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
import com.example.my_weather.util.IconUtils
import com.example.my_weather.util.SharedPrefsUtils

class SearchAdapter(
    private val onItemClick: (City) -> Unit
): ListAdapter<City, SearchAdapter.ViewHolder>(SearchDiff()) {

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
            binding.apply {
                tvCityName.text = city.name
                tvCountryName.text = city.country.name
                tvWeatherCondition.text = city.weathers[0].description
                ivWeatherIcon.load(
                    IconUtils.getWeatherIconUrl(city.weathers[0].icon)) {
                    crossfade(true)
                    placeholder(R.drawable.ic_weather_placeholder
                )}
                tvTempAmount.text = city.main.temperature
                tvTempUnit.text = SharedPrefsUtils.getTempUnitSearched()
                tvCloudPercentage.text = city.clouds.percentage.toString()
                tvWindSpeed.text = city.wind.speed.toString()

                itemView.setOnClickListener {
                    onItemClick(city)
                }
            }
        }
    }

    class SearchDiff: DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem == newItem

        override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem
    }


}