package com.example.weatherimageapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CityPagerAdapter(
    activity: FragmentActivity,
    private val cities: List<String>
) : FragmentStateAdapter(activity) {
    override fun getItemCount() = cities.size

    override fun createFragment(position: Int): Fragment {
        return CityWeatherFragment.newInstance(cities[position])
    }
}
