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
import com.example.my_weather.ui.main.model.CityRaw
import com.example.my_weather.util.FileUtils
import com.example.my_weather.util.MarginItemDecoration
import com.example.my_weather.util.SharedPrefsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SearchFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val searchAdapter by lazy { SearchAdapter { city ->
        val intent = Intent(this.requireContext(), ForecastActivity::class.java)
        city.apply {
            val cityRaw = CityRaw(
                this.id,
                this.name,
                this.country.name,
                this.main.temperature,
                SharedPrefsUtils.getTempUnitSearched(),
                city.weathers[0].icon
            )
            intent.putExtra("cityRaw", cityRaw)
            startActivity(intent)
        }
    }}

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

        // this will create a encrypted key for the weather api
        FileUtils.createEncrypted(
            this.requireContext(),
            "secretApiKey",
            "989c2cec50552dd532208380980ea229"
        )
    }

    private fun initUi() {
        binding.btnSearch.setOnClickListener {
            listCities()
            SharedPrefsUtils.updateTempUnitSearched(requireContext())
        }

        binding.rvCities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
            addItemDecoration(MarginItemDecoration(16.toPx()))
        }
    }

    private fun listCities() {
        val q = binding.edtSearchQuery.text.toString()
        val unit = SharedPrefsUtils.getUnitKey(requireContext())
        val lang = SharedPrefsUtils.getLangKey(requireContext())
        val key = FileUtils.readEncrypted(requireContext(),
            File(requireContext().filesDir, "secretApiKey")
        )

        if (requireContext().isInternetAvailable()) {
            val call = RetrofitManager.getOpenWeatherService().findCity(q, unit, lang, key)
            call.enqueue(object : Callback<FindResult> {
                override fun onResponse(call: Call<FindResult>, response: Response<FindResult>) {
                    if (response.isSuccessful) {
                        searchAdapter.submitList(response.body()?.cities)
                    } else {
                        Log.w("SearchFragment", "onResponse: ${response.message()} ")
                    }
                }

                override fun onFailure(call: Call<FindResult>, t: Throwable) {
                    Log.e("SearchFragment", "onFailure: ", t)
                }
            })
        } else {
            Toast.makeText(requireContext(), "No network access", Toast.LENGTH_SHORT).show()
        }
    }

}