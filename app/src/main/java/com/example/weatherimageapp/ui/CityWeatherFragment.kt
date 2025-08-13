package com.example.weatherimageapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherimageapp.R
import com.example.weatherimageapp.core.state.ApiState
import com.example.weatherimageapp.databinding.FragmentCityWeatherBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CityWeatherFragment : Fragment() {
    lateinit var binding: FragmentCityWeatherBinding
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
        cityName = arguments?.getString(ARG_CITY) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCityWeatherBinding.inflate(layoutInflater)
        cityName.let { city ->
            viewModel.loadCityData(city)
            lifecycleScope.launch {
                viewModel.apiState.collect {
                    when(it){
                        is ApiState.Error -> {}
                        ApiState.Loading -> {}
                        ApiState.Success -> {
                            val weatherImageData = viewModel.weatherImageData.value
                             if (weatherImageData != null) {
                                with(binding) {
                                    cityName.text = weatherImageData.name
                                    temp.text = "${weatherImageData?.temperature}°C"

                                    Picasso.get()
                                        .load( weatherImageData.imageUrl)
                                        .placeholder(R.drawable.placeholder) // drawable me ek default image rakh do
                                        .noFade() // Picasso ka default fade remove
                                        .into(cityImage, object : com.squareup.picasso.Callback {
                                            override fun onSuccess() {
                                                // Start from zoomed-in + transparent
                                                cityImage.scaleX = 1.2f
                                                cityImage.scaleY = 1.2f
                                                cityImage.translationY = 50f // thoda neeche se aayega
                                                cityImage.alpha = 0f

                                                // Animate to normal with fade + slight parallax feel
                                                cityImage.animate()
                                                    .alpha(1f)
                                                    .translationY(0f)
                                                    .scaleX(1f)
                                                    .scaleY(1f)
                                                    .setDuration(1200) // cinematic smooth
                                                    .setInterpolator(android.view.animation.DecelerateInterpolator())
                                                    .start()
                                            }

                                            override fun onError(e: Exception?) {
                                                e?.printStackTrace()
                                            }
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
        return binding.root
    }

}