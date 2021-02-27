package com.example.my_weather.ui.main.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.my_weather.data.remote.RetrofitManager
import com.example.my_weather.data.remote.model.FindResult
import com.example.my_weather.databinding.FragmentSearchBinding
import com.example.my_weather.extension.isInternetAvailable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSearch.setOnClickListener {
            findCity()
        }
    }

    private fun findCity() {
        if (requireContext().isInternetAvailable()) {
            val call = RetrofitManager.getOpenWeatherService().findCity(
                binding.edtSearch.text.toString(),
                "metric",
                "pt",
                "989c2cec50552dd532208380980ea229"
            )
            call.enqueue(object : Callback<FindResult> {
                override fun onResponse(call: Call<FindResult>, response: Response<FindResult>) {
                    if (response.isSuccessful) {
                        response.body()?.cities?.forEach {
                            Log.d(TAG, "onResponse: $it")
                        }
                    } else {
                        Log.w(TAG, "onResponse: ${response.message()} ")
                    }
                }

                override fun onFailure(call: Call<FindResult>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                }
            })
        } else {
            Toast.makeText(requireContext(), "No network access", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "SearchFragment"
    }

}