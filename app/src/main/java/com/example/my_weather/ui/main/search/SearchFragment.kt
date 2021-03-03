package com.example.my_weather.ui.main.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_weather.data.remote.RetrofitManager
import com.example.my_weather.data.remote.model.FindResult
import com.example.my_weather.databinding.FragmentSearchBinding
import com.example.my_weather.extension.isInternetAvailable
import com.example.my_weather.extension.toPx
import com.example.my_weather.ui.main.forecast.ForecastActivity
import com.example.my_weather.util.FileUtils
import com.example.my_weather.util.MarginItemDecoration
import com.example.my_weather.util.SharedPrefsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SearchFragment: Fragment() {

    companion object {
        private const val TAG = "SearchFragment"
    }

    private lateinit var binding: FragmentSearchBinding

    private val searchAdapter by lazy { SearchAdapter { city ->
        val intent = Intent(this.requireContext(), ForecastActivity::class.java)
        intent.putExtra("cityId", city.id)
        intent.putExtra("cityName", city.name)
        intent.putExtra("cityCountry", city.country.name)
        intent.putExtra("cityTempAmount", city.main.temperature)
        intent.putExtra("cityTempUnit", SharedPrefsUtils.getTempUnitSearched())
        intent.putExtra("cityTempIcon", city.weathers[0].icon)
        startActivity(intent)
    } }

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
        initUi()
        FileUtils.createEncrypted(
            this.requireContext(),
            "secretApiKey",
            "989c2cec50552dd532208380980ea229"
        )
    }

    private fun findCity() {
        if (requireContext().isInternetAvailable()) {
            val call = RetrofitManager.getOpenWeatherService().findCity(
                    binding.edtSearch.text.toString(),
                    SharedPrefsUtils.getUnitKey(requireContext(), SharedPrefsUtils.UNIT_KEY),
                    SharedPrefsUtils.getLangKey(requireContext(), SharedPrefsUtils.LANG_KEY),
                    FileUtils.readEncrypted(
                        requireContext(),
                        File(requireContext().filesDir, "secretApiKey")
                    )
            )
            call.enqueue(object : Callback<FindResult> {
                override fun onResponse(call: Call<FindResult>, response: Response<FindResult>) {
                    if (response.isSuccessful) {
                        searchAdapter.submitList(response.body()?.cities)
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

    private fun initUi() {
        binding.rvCities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
            addItemDecoration(MarginItemDecoration(16.toPx()))
        }

        binding.btnSearch.setOnClickListener {
            findCity()
            SharedPrefsUtils.updateTempUnitSearched(requireContext())
        }
    }

}