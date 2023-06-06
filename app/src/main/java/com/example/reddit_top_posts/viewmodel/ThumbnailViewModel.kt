package com.example.reddit_top_posts.viewmodel

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.reddit_top_posts.model.storage.ImageRepository
import javax.inject.Inject

class ThumbnailViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    fun saveImage(url: String, image: ImageView) {
        val imageName = url.split("/").last()
        imageRepository.save(imageName, image)
    }
}