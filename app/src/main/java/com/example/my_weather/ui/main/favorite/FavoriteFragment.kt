package com.example.my_weather.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_weather.data.local.DatabaseApp
import com.example.my_weather.data.local.model.Favorite
import com.example.my_weather.databinding.FragmentFavoriteBinding
import com.example.my_weather.extension.toPx
import com.example.my_weather.util.MarginItemDecoration

class FavoriteFragment: Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val favoriteAdapter by lazy { FavoriteAdapter {
        unfavorite(it)
    } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val dao = DatabaseApp.getInstance(requireContext()).getFavoriteDao()
        favoriteAdapter.submitList(dao.getAll())

        binding.apply {
            btnSearchFavoriteCity.setOnClickListener {
                searchFavorits(edtFavoriteCityName.text.toString())
            }

            rvFavoriteCities.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = favoriteAdapter
                addItemDecoration(MarginItemDecoration(16.toPx()))
            }
        }
    }

    private fun searchFavorits(q: String) {
        val dao = DatabaseApp.getInstance(requireContext()).getFavoriteDao()
        favoriteAdapter.submitList(dao.getAllFiltered(q))
    }

    private fun unfavorite(favorite: Favorite) {
        val dao = DatabaseApp.getInstance(requireContext()).getFavoriteDao()
        dao.delete(favorite)
        favoriteAdapter.submitList(dao.getAll())
        Toast.makeText(requireContext(), "Deleted from favorites", Toast.LENGTH_SHORT).show()
    }
}