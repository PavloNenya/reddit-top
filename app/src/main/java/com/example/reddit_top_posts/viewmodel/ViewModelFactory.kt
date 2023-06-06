package com.example.reddit_top_posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.reddit_top_posts.model.storage.ImageRepository
import java.lang.IllegalArgumentException

object ViewModelFactory  : ViewModelProvider.Factory {

    private val imageRepository = ImageRepository()

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when(modelClass) {
            ThumbnailViewModel::class.java -> {
                ThumbnailViewModel(imageRepository) as T
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}