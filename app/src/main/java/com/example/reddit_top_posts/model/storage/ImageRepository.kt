package com.example.reddit_top_posts.model.storage

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.widget.ImageView
import com.example.reddit_top_posts.config.SAVED_PICTURES_DIRECTORY
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageRepository @Inject constructor(){

    fun save(imageName: String, image: ImageView) {
        val file =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val redditDir = File(file.absolutePath, SAVED_PICTURES_DIRECTORY)
        if (!redditDir.exists()) {
            redditDir.mkdir()
        }
        val imageFile = File(file.absolutePath + '/' + SAVED_PICTURES_DIRECTORY, imageName)
        val draw = image.drawable as BitmapDrawable
        val bitmap = draw.bitmap
        val fileOutPutStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutPutStream)
        fileOutPutStream.flush()
        fileOutPutStream.close()
    }
}