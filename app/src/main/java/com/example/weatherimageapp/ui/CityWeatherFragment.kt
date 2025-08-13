package com.example.weatherimageapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherimageapp.R
import com.example.weatherimageapp.core.state.ApiState
import com.example.weatherimageapp.core.state.EMPTY
import com.example.weatherimageapp.core.state.collectLatestLifecycleFlow
import com.example.weatherimageapp.core.state.loadImageWithZoom
import com.example.weatherimageapp.data.CityWeatherWithImage
import com.example.weatherimageapp.databinding.FragmentCityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityWeatherFragment : Fragment() {
    private var _binding: FragmentCityWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var cityName: String
    private val viewModel: WeatherImageViewModel by viewModels()

    companion object {
        private const val ARG_CITY = "city"
        fun newInstance(city: String) = CityWeatherFragment().apply {
            arguments = Bundle().apply { putString(ARG_CITY, city) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = arguments?.getString(ARG_CITY) ?: String.Companion.EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCityWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (cityName.isNotBlank()) {
            viewModel.loadCityData(cityName)
        }

       /* lifecycleScope.launch {
            viewModel.apiState.collect { state->
                when(state) {
                    is ApiState.Error -> {}
                    ApiState.Loading -> {}
                    ApiState.Success -> {
                        bindWeatherData(viewModel.weatherImageData)
                    }
                }

            }
        }*/

        collectLatestLifecycleFlow(viewModel.apiState) { state ->
            when (state) {
                is ApiState.Loading -> {}
                is ApiState.Error -> {}
                is ApiState.Success -> bindWeatherData(viewModel.weatherImageData)
            }
        }
    }

    private fun bindWeatherData(weatherImageData: CityWeatherWithImage?) {
        weatherImageData?.let { data ->
            binding.cityName.text = data.name
            binding.temp.text = "${data.temperature}°C"
            binding.cityImage.loadImageWithZoom(
                url = data.imageUrl,
                placeholderRes = R.drawable.placeholder
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}