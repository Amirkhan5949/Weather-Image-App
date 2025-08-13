package com.example.weatherimageapp.core.state

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

val String.Companion.EMPTY: String get() = ""

fun ImageView.loadImageWithZoom(url: String, placeholderRes: Int) {
    Picasso.get().load(url).placeholder(placeholderRes).noFade()
        .into(this, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                scaleX = 1.2f
                scaleY = 1.2f
                translationY = 50f
                alpha = 0f

                animate().alpha(1f).translationY(0f).scaleX(1f).scaleY(1f).setDuration(1200)
                    .setInterpolator(android.view.animation.DecelerateInterpolator()).start()
            }

            override fun onError(e: Exception?) {
                e?.printStackTrace()
            }
        })
}

fun <T> Fragment.collectLatestLifecycleFlow(
    flow: Flow<T>,
    collect: suspend (T) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

