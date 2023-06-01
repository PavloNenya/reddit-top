package com.example.reddit_top_posts.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.reddit_top_posts.config.SAVED_PICTURES_DIRECTORY
import com.example.reddittopposts.R
import com.example.reddittopposts.databinding.FragmentThumbnailImageBinding
import java.io.File
import java.io.FileOutputStream

class ThumbnailFragment : Fragment() {
    private lateinit var binding: FragmentThumbnailImageBinding
    private val args: ThumbnailFragmentArgs by navArgs()
    private lateinit var thumbnailUrl: String
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private lateinit var zoomListener: ZoomListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThumbnailImageBinding.inflate(layoutInflater, container, false)
        zoomListener = ZoomListener(
            binding.thumbnailPicture,
            activity?.windowManager?.defaultDisplay?.height!!,
            activity?.windowManager?.defaultDisplay?.width!!
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thumbnailUrl = args.thumbnailUrl

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            thumbnailPicture.load(thumbnailUrl) {
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.error_picture)
                listener(
                    onSuccess = { imageRequest: ImageRequest, successResult: SuccessResult ->
                        progressBar.visibility = View.INVISIBLE
                        scaleGestureDetector =
                            ScaleGestureDetector(binding.root.context, zoomListener)
                        root.setOnTouchListener { view: View, event: MotionEvent ->
                            scaleGestureDetector?.onTouchEvent(event)
                            zoomListener.onTouch(view, event)
                            true
                        }
                    }
                )
            }

            downloadButton.setOnClickListener {
                val file =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val imageName = thumbnailUrl.split("/").last()
                val redditDir = File(file.absolutePath, SAVED_PICTURES_DIRECTORY)
                if (!redditDir.exists()) {
                    redditDir.mkdir()
                }
                val imageFile = File(file.absolutePath + '/' + SAVED_PICTURES_DIRECTORY, imageName)
                val draw = binding.thumbnailPicture.drawable as BitmapDrawable
                val bitmap = draw.bitmap
                val fileOutPutStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutPutStream)
                fileOutPutStream.flush()
                fileOutPutStream.close()
            }
        }
    }
}