package com.example.weatherimageapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherimageapp.assets.City
import com.example.weatherimageapp.databinding.WeatherImageItemBinding
import com.squareup.picasso.Picasso

class WeatherImageAdapter constructor(var item: List<City>) :
    RecyclerView.Adapter<WeatherImageAdapter.WeatherImageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WeatherImageViewHolder {
        val binding =
            WeatherImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherImageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WeatherImageViewHolder,
        position: Int,
    ) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int = item.size

    inner class WeatherImageViewHolder(val binding: WeatherImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            with(binding) {
                val data = item[position]
                cityName.text = data.name
                temp.text = data.temperature


                Picasso.get()
                    .load(data.imageUrl)
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