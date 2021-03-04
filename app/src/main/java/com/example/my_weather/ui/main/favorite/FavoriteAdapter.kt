package com.example.my_weather.ui.main.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.my_weather.data.local.model.Favorite
import com.example.my_weather.databinding.ItemFavoriteCityBinding

class FavoriteAdapter(
    private val onItemClick: (Favorite) -> Unit
): ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(SearchDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemFavoriteCityBinding): RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("SimpleDateFormat")
        fun bind(favorite: Favorite) {
            binding.apply {
                tvCityName.text = favorite.cityName
                tvCountryName.text = favorite.cityCountry
                btnDeleteFavorite.setOnClickListener {
                    onItemClick(favorite)
                }
            }
        }
    }

    class SearchDiff: DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite) = oldItem == newItem
    }


}