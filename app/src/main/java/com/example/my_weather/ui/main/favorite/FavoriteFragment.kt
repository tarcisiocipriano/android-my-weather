package com.example.my_weather.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.my_weather.databinding.FragmentFavoriteBinding
import com.example.my_weather.databinding.FragmentSearchBinding

class FavoriteFragment: Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}